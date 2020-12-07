package ru.devag.kamc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.devag.kamc.rent.*;
import ru.devag.kamc.nto.*;
import ru.devag.kamc.prclcost.PrclCostItem;
import ru.devag.kamc.rent.PropertyInfo.PropType;
import ru.devag.kamc.model.*;
import ru.devag.kamc.repo.*;
import ru.devag.kamc.smbusiness.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ImportService {
   private static Logger logger = LoggerFactory.getLogger(ImportService.class);

   public static String CADNUM = "\\d{2}:\\d{2}:\\d{7}:\\d{1,}$";

   @PersistenceContext
   private EntityManager entityManager;

   @Autowired
   private JdbcTemplate jdbcTemplate;

   @Autowired
   SubjectUtils sbjUtils;
   
   @Autowired
   ObjectSearch objSearch;

   @Autowired
   ObjectCreate objCreate;

   @Autowired
   RelationUtils rtnUtils;

   @Autowired
   I3SubjectRepository sbjRepo;

   @Autowired
   I3ObjectRepository objRepo;

   @Autowired
   I3BasementRepository bstRepo;

   @Autowired
   I3LptyComponentRepository lptyRepo;

   @Autowired
   I3LptyProtocolRepository lptyProtoRepo;

   @Autowired
   I3LptyPtlObjRepository lptyProtoObjRepo;

   @Autowired
   I3LptyPaymentRepository lptyPaymRepo;

   @Autowired
   I3CategoryRepository catRepo;

   @Autowired
   I3ClassifierValueRepository clfRepo;

   @Autowired
   I3SbjBstRepository sbjBstRepo;

   @Autowired
   I3SbjContractorRepository contractorRepo;

   @Autowired
   I3ObjBstRepository objBstRepo;

   @Autowired
   I3ObjBstTraitRepository objBstTraitRepo;

   @Autowired
   I3LandComponentRepository landRepo;

   @Autowired
   private I3RtnBstRepository rbsRepo;

   @Autowired
   I3NstoComponentRepository nstoRepo;

   @Autowired
   I3NstoProtocolRepository nstoProtoRepo;

   @Autowired
   I3PaymMethodicsRepository paymMethRepo;

   @Autowired
   I3CountCoeffRepository coeffRepo;

   @Autowired
   I3NstoKoefRepository nstoKoefRepo;

   @Autowired
   I3CntrComponentRepository cntrRepo;

   @Autowired
   I3PaymComponentRepository paymRepo;

   @Autowired
   I3PrclCostRepository prclCostRepo;

   private ConcurrentHashMap<String, BookInfo> cache = new ConcurrentHashMap<>();

   private Long depSbjId = -1L;
   private Long pkgoSbjId = -1L;
   private Long lptyCatId = -1L;
   private Long lptyClfId = -1L;
   private Long orderId = null;

   boolean createNew = false;
   boolean createNetw = false;
   boolean ignoreAll = false;
   boolean enableAddressSearch = true;

   boolean smbEnableCntr = true;
   boolean smbEnablePaym = true;

   int prclCostYear;
   String prclCostDescription;


   public void initRent(Map<String, Object> settings) {
      this.ignoreAll = (Boolean)settings.getOrDefault("ignoreAll", false);
      this.createNew = (Boolean)settings.getOrDefault("createNew", false);
      this.createNetw = (Boolean)settings.getOrDefault("createNetw", false);
      this.enableAddressSearch = (Boolean)settings.getOrDefault("enableAddressSearch", true);

      int threshFull = (Integer)settings.getOrDefault("threshFull", 10);

      sbjUtils.init();
      objSearch.init(threshFull);

      lptyCatId = catRepo.findByCatCode("LPTY").getId();
      lptyClfId = clfRepo.findByCfvCode("CLF_CNTRTYPE_LEA_NF").getId();

      depSbjId = sbjUtils.getDepSbjId();
      pkgoSbjId = sbjUtils.getPKGOSbjId();

      Optional<I3Basement> doc = bstRepo.findById(58121073L);
      if (doc.isPresent()) {
         this.orderId = doc.get().getId();
      } else {
         this.orderId = null;
      }
   }

   public void initNto(Map<String, Object> settings) {
   }
   
   public void initSmb(Map<String, Object> settings) {
      this.smbEnableCntr = (Boolean)settings.getOrDefault("enableCntr", true);
      this.smbEnablePaym = (Boolean)settings.getOrDefault("enablePaym", true);
   }

   public void initPrclCost(Map<String, Object> settings) {
      this.prclCostYear = (int)settings.getOrDefault("year", Year.now().getValue() + 1);
      this.prclCostDescription = (String)settings.getOrDefault("description", null);
      objSearch.initPrclCache();
   }

   public void put(String code, BookInfo bookInfo) {
      cache.put(code, bookInfo);
   }

   public BookInfo get(String code) {
      return cache.get(code);
   }

   @Transactional
   public I3NstoProtocol importNtoItem(NtoItem item, NtoSchemeSheet schemeSheet, Map<String, Integer> cntrNums, List<String> ignored, List<String> created) {
      logger.info("Договор: {}", item.getCntrNum());

      I3Subject sbj = sbjUtils.getSbj(item.getInn(), null, item.getSbj());
      if (sbj == null) {
         sbj = sbjUtils.createSbj(item);
         logger.warn("Новый субъект: {}", item.getSbj());
      }

      Optional<NtoSchemeItem> maybeSchemeItem = schemeSheet.items.stream()
         .filter(schemeItem -> schemeItem.getNum() != null && schemeItem.getNum().equals(item.getScheme())).findAny();
      if (!maybeSchemeItem.isPresent()) {
         throw new RuntimeException("Item's scheme not found: " + item.getCntrNum());
      }

      I3NobjComponent nobj = objCreate.getOrCreateNobj(maybeSchemeItem.get(), item);

      I3Basement bst = new I3Basement();
      bst.setCatCategoryId(catRepo.findByCatCode("NSTO").getId());
      bst.setBstNumber("XLSX_2019_NSTO_" + item.getRowId());
      bst.setStsStatusId(1L);
      bstRepo.save(bst);
      
      I3NstoComponent nsto = new I3NstoComponent();
      nsto.setBstBasementId(bst.getId());
      nsto.setNsoNumber(String.valueOf(item.getCntrNum()));
      nsto.setNsoConfirmDate(item.getConfirmDate());
      nsto.setNsoEndDate(item.getEndDate());

      I3ClassifierValue clfType = clfRepo.findByCfvCode("CLF_CNTRTYPE_NTO");
      List<I3ClassifierValue> types;
      if (!StringUtils.isEmpty(item.getNotes()) && item.getNotes().contains("преим")) {
         types = clfRepo.findByCfvValueAndCfvParentId("преимущественное право", clfType.getId());
      } else {
         types = clfRepo.findByCfvValueAndCfvParentId("аукцион", clfType.getId());
      }
      
      nsto.setNsoCfvTypeId(types.get(0).getId());
      nstoRepo.save(nsto);

      I3ObjBst objBst = new I3ObjBst();
      objBst.setObbType(2105L);
      objBst.setObjObjectId(nobj.getObject().getId());
      objBst.setBstBasementId(bst.getId());
      objBstRepo.save(objBst);

      I3SbjBst sbjBst = new I3SbjBst();
      sbjBst.setSbjSubjectId(sbj.getId());
      sbjBst.setBstBasementId(bst.getId());
      sbjBst.setSbbType(4109L);
      sbjBstRepo.save(sbjBst);

      return createNstoProtocol(item, nsto, sbjBst.getId(), sbj.getId(), nobj.getObject().getId());
   }

   @Transactional
   public void calcNstoProto(I3NstoProtocol proto) {
      /*SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
         .withCatalogName("kamc_calc")
         .withProcedureName("SetEnv");
      jdbcCall.execute(30, 360);*/
         
      SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
         .withCatalogName("kamc_calc")
         .withFunctionName("CalcNstoPaym");
      
      jdbcCall.executeFunction(Long.class, proto.getId());
   }

   @Transactional
   public void importRentSheet(RentSheet sheet, List<String> ignored, List<String> created) {
      I3Subject sbj = sbjUtils.getSbj(sheet.inn, null, sheet.subject);
      if (sbj == null) {
         logger.error("Не удалось найти субъекта: {}", sheet.subject);
         return;
      }

      //logger.info("Импорт [{}]", sheet.cntrNum);

      I3Basement bst = new I3Basement();
      I3LptyComponent lpty = new I3LptyComponent();

      bst.setBstNumber(sheet.cntrNum);
      bst.setCatCategoryId(lptyCatId);
      bst.setStsStatusId(1L);
      
      lpty.setLpyIsOtherRate("F");
      lpty.setCfvClassifierValueId(lptyClfId);

      lpty.setLpyConfirmDate(sheet.cntrStartDate);
      lpty.setLpyEndDate(sheet.cntrEndDate);

      bstRepo.save(bst);
      lpty.setBstBasementId(bst.getId());
      lptyRepo.save(lpty);

      I3SbjBst sbjBst = new I3SbjBst();
      sbjBst.setSbjSubjectId(depSbjId);
      sbjBst.setBstBasementId(bst.getId());
      sbjBst.setSbbType(4110L);
      sbjBstRepo.save(sbjBst);

      sbjBst = new I3SbjBst();
      sbjBst.setSbjSubjectId(sbj.getId());
      sbjBst.setBstBasementId(bst.getId());
      sbjBst.setSbbType(4109L);
      sbjBstRepo.save(sbjBst);

      I3SbjContractor sbjContractor = new I3SbjContractor();
      sbjContractor.setSbbSbjBstId(sbjBst.getId());
      sbjContractor.setSbcBusIsPers("F");
      sbjContractor.setSbcIsFree("F");
      contractorRepo.save(sbjContractor);

      I3LptyProtocol proto = createLptyProtocol(sheet, lpty, sbjBst.getId(), sbj);
      createLptyPayments(proto, sheet);

      for (PropertyInfo property: sheet.items) {
         Long objId = getObjId(property, sbj.getId());

         if (objId != null && objId > 0) {
            I3ObjBst objBst = new I3ObjBst();
            objBst.setObbType(2105L);
            objBst.setObjObjectId(objId);
            objBst.setBstBasementId(bst.getId());
            objBstRepo.save(objBst);

            I3ObjBstTrait objBstTrait = new I3ObjBstTrait();
            objBstTrait.setObbObjBstId(objBst.getId());
            objBstTrait.setObtIsPartial(property.isPart ? "T" : "F");
            objBstTraitRepo.save(objBstTrait);

            rtnUtils.createRent(true, sheet, property, objId, sbj.getId(), bst.getId());
            addProtoObject(proto, objId, property);
         } else {
            if (!createNew && !ignoreAll) {
               logger.error("not found: {}", property.propName);
               throw new RuntimeException("Не найден хотя бы один объект");
            } else {
               if (createNew) {
                  if ((property.propType == PropType.NETW && createNetw) || 
                     (property.propType != PropType.APRM && property.propType != PropType.NETW)) {

                     I3Object obj;
                     if (property.propType == PropType.NETW) {
                        obj = objCreate.createNetw(sheet, property);
                     } else {
                        obj = objCreate.createKfxa(sheet, property);
                     }
                     
                     created.add("[" + sheet.cntrNum + "] " + property.propNum + " " + property.propName);
                     logger.warn("create [{}] {}", property.propNum, obj.getObjDescription());
                     objId = obj.getId();

                     rtnUtils.createRent(false, sheet, property, obj.getId(), sbj.getId(), bst.getId());
                     rtnUtils.createMS(sheet, property, obj.getId(), pkgoSbjId);
                     Long rtnId = rtnUtils.createMK(sheet, property, obj.getId(), depSbjId);
                     
                     if (property.propType == PropType.NETW && orderId != null) {
                        I3RtnBst rbs = new I3RtnBst(rtnId, orderId, 3204L);
                        rbsRepo.save(rbs);
                     }

                     rtnUtils.createTrat(sheet, property, objId);
                     addProtoObject(proto, objId, property);
                  } else {
                     if (ignoreAll) {
                        objId = 5L;
                     }
                     logger.warn("игнор создание не иного [{}] {}", property.propNum, property.propName);
                  }
               } else if (ignoreAll) {
                  objId = 5L;
                  ignored.add("[" + sheet.cntrNum + "] " + property.propNum + " " + property.propName);
               }

               if (objId > 0) {
                  I3ObjBst objBst = new I3ObjBst();
                  objBst.setObbType(2105L);
                  objBst.setObjObjectId(objId);
                  objBst.setBstBasementId(bst.getId());
                  objBstRepo.save(objBst);
               }
            }
         }
      }

   }

   @Transactional
   public void importSmbItem(SmbItem item, Map<String, Integer> sheetCodes, List<String> ignored, List<String> created) {
      logger.info("№ п/п: {}, {}", item.getPos(), item.getSubject());

      I3Subject sbj = sbjUtils.getSbj(item.getInn(), item.getOgrn(), null); //ищем только по ИНН и ОГРН, без описания
      if (sbj == null) {
         logger.warn("Cубъект [{}] не найден ни по ИНН, ни по ОГРН: {} / {}", item.getSubject(), item.getInn(), item.getOgrn());
      } else {
         if (smbEnableCntr) {
            List<Tuple> sbjBsts = cntrRepo.findSbbBySbjId(sbj.getId());
            if (sbjBsts.size() > 0) {
               StringBuilder sb = new StringBuilder();
               for (Tuple entry: sbjBsts) {
                  I3SbjBst sbb = entry.get(0, I3SbjBst.class);
                  I3CntrComponent cntr = entry.get(1, I3CntrComponent.class);
                  
                  if (sb.length() > 0) {
                     sb.append(", ");
                  }
                  sb.append(cntr.getCnrNumber());
                  List<I3SbjContractor> contractors = contractorRepo.findBySbbSbjBstId(sbb.getId());
                  I3SbjContractor contractor;
                  if (contractors.size() == 0) {
                     sb.append("(+)");
                     contractor = new I3SbjContractor();
                     contractor.setSbbSbjBstId(sbb.getId());
                     contractor.setSbcBusIsPers("F");
                     contractor.setSbcIsFree("F");
                  } else {
                     contractor = contractors.get(0);
                  }
                  contractor.setSbcSmallBusinessType(getSmbCode(item.getCategory()));
                  contractor.setSbcSmallBusinessRegDate(item.getRegDate());
                  contractorRepo.save(contractor);
               }
               logger.warn("Договоры: {}", sb);
            }
         }

         if (smbEnablePaym) {
            List<I3PaymComponent> payms = paymRepo.findBySbjId(sbj.getId());
            Map<String, Integer> cntrNums = new HashMap<>();
            Map<String, Integer> cntrSkippedNums = new HashMap<>();
            for (I3PaymComponent paym: payms) {
               List<I3CntrComponent> cntrs = cntrRepo.findByPaymBstId(paym.getBst().getId());
               if (cntrs.size() > 0) {
                  String cntrNum = cntrs.get(0).getCnrNumber();
                  if (!paym.getBst().getBstDate().before(item.getRegDate())) {
                     paym.setPacSbjSmBusinessType(getSmbCode(item.getCategory()));
                     paym.setPacSbjSmBusinessRegDate(item.getRegDate());
                     paymRepo.save(paym);
   
                     cntrNums.compute(cntrNum, (k, v) -> v == null ? 1 : v + 1);
                  } else {
                     cntrSkippedNums.compute(cntrNum, (k, v) -> v == null ? 1 : v + 1);
                  }
               }
            }
            
            if (cntrNums.size() > 0) {
               logger.warn("Протоколы: {}", cntrNums.entrySet().stream().map(entry -> entry.getKey() + "(" + entry.getValue() + ")").collect(Collectors.joining(", ")));
            }
            if (cntrSkippedNums.size() > 0) {
               logger.warn("Протоколы пропущены: {}", cntrSkippedNums.entrySet().stream().map(entry -> entry.getKey() + "(" + entry.getValue() + ")").collect(Collectors.joining(", ")));
            }
            
         }
      }
   }

   @Transactional
   public void importPrclCostItem(PrclCostItem item, Map<String, Integer> sheetCodes, List<String> notFound, List<String> created) {
      Double cadCost = ImportUtils.getNumeric(item.getCadcost());
      List<Long> prclIds = objSearch.getPrclIdsByCadnumAndCostYear(item.getCadnum().trim(), prclCostYear);
      if (prclIds == null || prclIds.size() == 0) {
         notFound.add(item.getCadnum());
      } else {
         created.add(item.getCadnum());
         for (Long prclId: prclIds) {
            I3PrclCost prclCost = new I3PrclCost();
            prclCost.setPrcPrclComponentId(prclId);
            prclCost.setPcoCadCost(cadCost);
            prclCost.setPcoDescription(prclCostDescription);
            prclCost.setPcoDate(new GregorianCalendar(prclCostYear, 0, 1).getTime());
            prclCostRepo.save(prclCost);
         }
      }
   }
   
   private String getSmbCode(String category) {
      switch (category) {
         case "Микропредприятие":
            return "MICRO";
         case "Среднее предприятие":
            return "MEDIUM";
         default:
            return "SMALL";
      }
   }

   private Long getObjId(PropertyInfo property, Long sbjId) {
      Long objIdByCadnum = objSearch.findObjByCadnum(property);
      if (objIdByCadnum > 0) {
         return objIdByCadnum;
      }
      
      Set<Long> sbjObjIds = sbjUtils.getSbjObjects(sbjId);

      Long objIdByCost = objSearch.findObjByCost(property, sbjObjIds);
      if (objIdByCost > 0)
         return objIdByCost;

      Long objIdByName = objSearch.findObjByName(property, true, sbjObjIds);
      if (objIdByName > 0)
         return objIdByName;

      if (enableAddressSearch) {
         Long objIdByAddress = objSearch.findObjByAddress(property);
         if (objIdByAddress > 0) {
            return objIdByAddress;
         }
      }

      objIdByName = objSearch.findObjByName(property, false, sbjObjIds);
      if (objIdByName > 0)
         return objIdByName;

      if (property.propType == PropType.NETW) {
         Long objIdByLength = objSearch.findObjByLength(property, sbjObjIds);
         if (objIdByLength > 0)
            return objIdByLength;
      }

      return -1L;
   }

   private I3LptyProtocol createLptyProtocol(RentSheet sheet, I3LptyComponent lpty, Long sbbId, I3Subject sbj) {
      SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
      I3LptyProtocol proto = new I3LptyProtocol();
      proto.setLpty(lpty);

      try {
         proto.setPtlStartDate(sdf.parse("01.01.2019"));
         proto.setPtlEndDate(sdf.parse("31.12.2019"));
      } catch (ParseException ignored) {}

      proto.setSbbSbjBstId(sbbId);
      proto.setSbj(sbj);
      proto.setPtlPeriodRate(sheet.cntrMonthSum);
      proto.setPtlYearRate(sheet.cntrYearSum);
      proto.setPtlVat(20d);

      lptyProtoRepo.save(proto);
      return proto;
   }

   private void addProtoObject(I3LptyProtocol proto, Long objId, PropertyInfo property) {
      I3LptyPtlObj ptlObj = new I3LptyPtlObj();
      ptlObj.setProto(proto);
      ptlObj.setObjObjectId(objId);

      if (property.propMonthSum != null && property.propMonthSum > 0) {
         ptlObj.setPtoPeriodRate(property.propMonthSum);
      }

      if (property.propYearSum != null && property.propYearSum > 0) {
         ptlObj.setPtoYearRate(property.propYearSum);
      }
      lptyProtoObjRepo.save(ptlObj);
   }

   private void createLptyPayments(I3LptyProtocol proto, RentSheet sheet) {
      SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

      for (int month = 0; month < 12; month++) {
         I3LptyPayment paym = new I3LptyPayment();
         paym.setProto(proto);
         paym.setPmtNumber("01/" + String.format("%02d", month + 1));
         try {
            paym.setPmtPaymentStart(sdf.parse("01." + String.valueOf(month + 1) + ".2019"));
            paym.setPmtPaymentEnd(sdf.parse("15." + String.valueOf(month + 1) + ".2019"));
         } catch (ParseException ignored) {}

         paym.setPmtExpectedVal(sheet.cntrMonthSum);
         lptyPaymRepo.save(paym);
      }
   }

   public static String getCadnum(String val) {
      if (val.matches(CADNUM)) {
         return val.substring(0, 14) + StringUtils.leftPad(val.substring(14), 5, "0");
      } else {
         return val;
      }
   }

   private I3NstoProtocol createNstoProtocol(NtoItem item, I3NstoComponent nsto, Long sbbId, Long sbjId, Long objId) {
      I3NstoProtocol proto = new I3NstoProtocol();
      SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
      proto.setNsoNstoComponentId(nsto.getId());

      Date yearBegin, yearEnd;
      try {
         yearBegin = sdf.parse("01.01.2019");
         yearEnd = sdf.parse("31.12.2019");
      } catch (ParseException e) {
         throw new RuntimeException(e);
      }
      
      proto.setPtlCreateDate(yearBegin);
      proto.setPtlStartDate(item.getPayStartDate().before(yearBegin) ? yearBegin : item.getPayStartDate());
      proto.setPtlEndDate(item.getEndDate().after(yearEnd) ? yearEnd : item.getEndDate());
      
      proto.setPtlPeriod(item.getDaysYear().longValue());
      long days = 1 + TimeUnit.DAYS.convert(proto.getPtlEndDate().getTime() - proto.getPtlStartDate().getTime(), TimeUnit.MILLISECONDS);
      if (item.getDaysYear().longValue() != days) {
         logger.warn("Число дней в протоколе отличается. В файле: {}, подсчитано: {}", item.getDaysYear().longValue(), days);
      }
      //proto.setPtlPeriod(days);

      proto.setSbbSbjBstId(sbbId);
      proto.setSbjSubjectId(sbjId);
      proto.setObjObjectId(objId);
      
      proto.setPtlYearRate(item.getCostYear());
      proto.setPtlArea(item.getArea());

      proto.setPtlDescription(String.format("%.2f", item.getCostYear()).replace(",", "."));

      I3PaymMethodics ntoMeth = paymMethRepo.findByPamCode("NSTO_AREA");
      proto.setPtlMethodics(ntoMeth.getId());

      nstoProtoRepo.save(proto);

      List<I3CountCoeff> coeffs = coeffRepo.findByCocModuleAndCocUse("NSTO", "T");
      for (I3CountCoeff coeff: coeffs) {
         I3NstoKoef koef = new I3NstoKoef();
         koef.setCocCountCoeffId(coeff.getId());
         koef.setKofCode(coeff.getCocCode());
         koef.setKofUse("T");
         koef.setPtlProtocolId(proto.getId());

         koef.setKofValue(coeff.getCocDefault());
         
         switch (coeff.getCocCode()) {
            case "Сср":
               if (item.getCadCostAVG() != null) koef.setKofValue(item.getCadCostAVG());
               break;
            case "Ктип":
               if (item.getKioskType() != null) koef.setKofValue(item.getKioskType().doubleValue());
               break;
            case "Кi":
               if (item.getCoeff() != null) koef.setKofValue(item.getCoeff());
               break;
         }

         nstoKoefRepo.save(koef);
      }

      return proto;
   }      
}