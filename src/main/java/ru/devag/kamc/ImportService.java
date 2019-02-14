package ru.devag.kamc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.devag.kamc.PropertyInfo.PropType;
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
   SubjectSearch sbjSearch;
   
   @Autowired
   ObjectSearch objSearch;

   @Autowired
   ObjectCreate objCreate;

   @Autowired
   I3SubjectRepository sbjRepo;

   @Autowired
   I3ObjectRepository objRepo;

   @Autowired
   I3BasementRepository bstRepo;

   @Autowired
   I3LptyComponentRepository lptyRepo;

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

   private ConcurrentHashMap<String, BookInfo> cache = new ConcurrentHashMap<>();

   private Long landlordId = -1L;
   private Long lptyCatId = -1L;
   private Long lptyClfId = -1L;

   boolean createNew = false;
   boolean ignoreAll = false;

   public void init(Map<String, Object> settings) {
      this.ignoreAll = (Boolean)settings.getOrDefault("ignoreAll", false);
      this.createNew = (Boolean)settings.getOrDefault("createNew", false);

      int threshFull = (Integer)settings.getOrDefault("threshFull", 10);

      sbjSearch.init();
      objSearch.init(threshFull);

      lptyCatId = catRepo.findByCatCode("LPTY").getId();
      lptyClfId = clfRepo.findByCfvCode("CLF_CNTRTYPE_LEA_NF").getId();

      landlordId = sbjSearch.getLandlordId();
   }

   public void put(String code, BookInfo bookInfo) {
      cache.put(code, bookInfo);
   }

   public BookInfo get(String code) {
      return cache.get(code);
   }

   @Transactional
   public void importSheet(SheetInfo sheet, List<String> ignored, List<String> created) {
      Long sbjId = sbjSearch.getSbjId(sheet);
      if (sbjId < 0)
         return;

      //logger.info("Импорт [{}]", sheet.cntrNum);

      SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

      I3Basement bst = new I3Basement();
      I3LptyComponent lpty = new I3LptyComponent();

      bst.setBstNumber(sheet.cntrNum);
      bst.setCatCategoryId(lptyCatId);
      bst.setStsStatusId(1L);
      
      lpty.setLpyIsOtherRate("F");
      lpty.setCfvClassifierValueId(lptyClfId);

      try {
         lpty.setLpyConfirmDate(dateFormat.parse(sheet.cntrStartDate));
      } catch (ParseException e) {
         logger.error("Wrong date: {}", sheet.cntrStartDate);
         lpty.setLpyConfirmDate(new Date());
      }

      if (!StringUtils.isEmpty(sheet.cntrEndDate) && !sheet.cntrEndDate.equals("неопределенный срок")) {
         try {
            lpty.setLpyEndDate(dateFormat.parse(sheet.cntrEndDate));
         } catch (ParseException e) {
            logger.error("Wrong date: {}", sheet.cntrEndDate);
         }
      }

      bstRepo.save(bst);
      lpty.setBstBasementId(bst.getId());
      lptyRepo.save(lpty);

      I3SbjBst sbjBst = new I3SbjBst();
      sbjBst.setSbjSubjectId(landlordId);
      sbjBst.setBstBasementId(bst.getId());
      sbjBst.setSbbType(4110L);
      sbjBstRepo.save(sbjBst);

      sbjBst = new I3SbjBst();
      sbjBst.setSbjSubjectId(sbjId);
      sbjBst.setBstBasementId(bst.getId());
      sbjBst.setSbbType(4109L);
      sbjBstRepo.save(sbjBst);

      I3SbjContractor sbjContractor = new I3SbjContractor();
      sbjContractor.setSbbSbjBstId(sbjBst.getId());
      sbjContractor.setSbcBusIsPers("F");
      sbjContractor.setSbcIsFree("F");
      contractorRepo.save(sbjContractor);

      for (PropertyInfo property: sheet.property) {
         Long objId = getObjId(property, sbjId);

         if (objId != null && objId > 0) {
            I3ObjBst objBst = new I3ObjBst();
            objBst.setObbType(2105L);
            objBst.setObjObjectId(objId);
            objBst.setBstBasementId(bst.getId());
            objBstRepo.save(objBst);
         } else {
            if (!createNew && !ignoreAll) {
               logger.error("not found: {}", property.propName);
               throw new RuntimeException("Не найден хотя бы один объект");
            } else {
               if (createNew) {
                  if (property.propType != PropType.APRM && property.propType != PropType.NETW) {
                     I3Object obj = objCreate.createKfxa(sheet, property);
                     created.add("[" + sheet.cntrNum + "] " + property.propNum + " " + property.propName);
                     logger.warn("create [{}] {}", property.propNum, obj.getObjDescription());
                     objId = obj.getId();
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

      Long objIdByAddress = objSearch.findObjByAddress(property);
      if (objIdByAddress > 0) {
         return objIdByAddress;
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

   public static String getCadnum(String val) {
      if (val.matches(CADNUM)) {
         return val.substring(0, 14) + StringUtils.leftPad(val.substring(14), 5, "0");
      } else {
         return val;
      }
   }
  
}