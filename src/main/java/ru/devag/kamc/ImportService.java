package ru.devag.kamc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.devag.kamc.rent.*;
import ru.devag.kamc.rent.PropertyInfo.PropType;
import ru.devag.kamc.model.*;
import ru.devag.kamc.repo.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ImportService {
   private static Logger logger = LoggerFactory.getLogger(ImportService.class);

   public static String CADNUM = "\\d{2}:\\d{2}:\\d{7}:\\d{1,}$";

   @PersistenceContext
   private EntityManager entityManager;

   @Autowired
   SubjectUtils sbjSearch;
   
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
   I3LandComponentRepository landRepo;

   @Autowired
   private I3RtnBstRepository rbsRepo;

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

   public void init(Map<String, Object> settings) {
      this.ignoreAll = (Boolean)settings.getOrDefault("ignoreAll", false);
      this.createNew = (Boolean)settings.getOrDefault("createNew", false);
      this.createNetw = (Boolean)settings.getOrDefault("createNetw", false);
      this.enableAddressSearch = (Boolean)settings.getOrDefault("enableAddressSearch", true);

      int threshFull = (Integer)settings.getOrDefault("threshFull", 10);

      sbjSearch.init();
      objSearch.init(threshFull);

      lptyCatId = catRepo.findByCatCode("LPTY").getId();
      lptyClfId = clfRepo.findByCfvCode("CLF_CNTRTYPE_LEA_NF").getId();

      depSbjId = sbjSearch.getDepSbjId();
      pkgoSbjId = sbjSearch.getPKGOSbjId();

      Optional<I3Basement> doc = bstRepo.findById(58121073L);
      if (doc.isPresent()) {
         this.orderId = doc.get().getId();
      } else {
         this.orderId = null;
      }
      
   }

   public void put(String code, BookInfo bookInfo) {
      cache.put(code, bookInfo);
   }

   public BookInfo get(String code) {
      return cache.get(code);
   }

   @Transactional
   public void importSheet(RentSheet sheet, List<String> ignored, List<String> created) {
      I3Subject sbj = sbjSearch.getSbj(sheet);
      if (sbj == null)
         return;

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

      I3LptyProtocol proto = createProtocol(sheet, lpty, sbjBst.getId(), sbj);
      createPayments(proto, sheet);

      for (PropertyInfo property: sheet.items) {
         Long objId = getObjId(property, sbj.getId());

         if (objId != null && objId > 0) {
            I3ObjBst objBst = new I3ObjBst();
            objBst.setObbType(2105L);
            objBst.setObjObjectId(objId);
            objBst.setBstBasementId(bst.getId());
            objBstRepo.save(objBst);

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

   private Long getObjId(PropertyInfo property, Long sbjId) {
      Long objIdByCadnum = objSearch.findObjByCadnum(property);
      if (objIdByCadnum > 0) {
         return objIdByCadnum;
      }
      
      Set<Long> sbjObjIds = sbjSearch.getSbjObjects(sbjId);

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

   private I3LptyProtocol createProtocol(RentSheet sheet, I3LptyComponent lpty, Long sbbId, I3Subject sbj) {
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

   private void createPayments(I3LptyProtocol proto, RentSheet sheet) {
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
  
}