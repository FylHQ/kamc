package ru.devag.kamc;

import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.devag.kamc.model.*;
import ru.devag.kamc.repo.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImportService {
   private static Logger logger = LoggerFactory.getLogger(ImportService.class);

   public static String CADNUM = "\\d{2}:\\d{2}:\\d{7}:\\d{1,}$";

   @PersistenceContext
   private EntityManager entityManager;

   @Autowired
   I3SubjectRepository sbjRepo;

   @Autowired
   I3CmpyComponentRepository cmpyRepo;

   @Autowired
   I3PersComponentRepository persRepo;

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
   I3ObjectRepository objRepo;

   @Autowired
   I3ObjBstRepository objBstRepo;

   @Autowired
   I3AprmComponentRepository aprmRepo;

   @Autowired
   I3NetwComponentRepository netwRepo;

   @Autowired
   I3LandComponentRepository landRepo;

   Map<Long, Map<String, List<I3Object>>> subjectsObj = new HashMap<>();
   //Map<String, List<I3Object>> allObj = null;
   Map<String, List<Long>> allObj = null;

   private ConcurrentHashMap<String, BookInfo> cache = new ConcurrentHashMap<>();

   private Long landlordId = -1L;
   private Long lptyCatId = -1L;
   private Long lptyClfId = -1L;

   public void initConstants() {
      if (allObj != null)
         return;

      I3Category cat = catRepo.findByCatCode("LPTY");
      lptyCatId = cat.getId();

      I3ClassifierValue clfLpty = clfRepo.findByCfvCode("CLF_CNTRTYPE_LEA_NF");
      lptyClfId = clfLpty.getId();

      List<I3Subject> landlordList = sbjRepo.findBySbjDescriptionIgnoreCaseContaining("Управление экономического развития");
      if (landlordList.size() != 1) {
         logger.error("Не найден арендодатель");
      }
      landlordId = landlordList.get(0).getId();

      logger.info("Чтение описаний всех объектов. Начало");
      allObj = new HashMap<>();
      Iterable<I3Object> it = objRepo.findAll();
      logger.info("Чтение описаний всех объектов. Окончание");
      for (I3Object o: it) {
         String descr = o.getObjDescription();
         if (descr != null) {
            allObj.computeIfAbsent(descr.toLowerCase().trim(), f -> new ArrayList<>())
               .add(o.getId());
         }
      }
   }


   public void put(String code, BookInfo bookInfo) {
      cache.put(code, bookInfo);
   }

   public BookInfo get(String code) {
      return cache.get(code);
   }

   @Transactional
   public void importSheet(SheetInfo sheet) {
      initConstants();

      Long sbjId = getSbjId(sheet);
      if (sbjId < 0)
         return;

      logger.info("Импорт [{}]", sheet.cntrNum);

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

         if (objId == null || objId < 0) {
            throw new RuntimeException("Не найден хотя бы один объект");
         }

         I3ObjBst objBst = new I3ObjBst();
         objBst.setObbType(2105L);
         objBst.setObjObjectId(objId);
         objBst.setBstBasementId(bst.getId());
         objBstRepo.save(objBst);

         //objRepo.findByObjDescriptionIgnoreCase(property.propName);
      }

   }

   private Long getObjId(PropertyInfo property, Long sbjId) {
      List<I3AprmComponent> aprms = aprmRepo.findByApmCadastralInfo(property.propCadnum);
      if (aprms.size() > 0) {
         logger.info("ok aprm cad: {}", property.propName);
         return aprms.get(0).getObjObjectId();
      } 
      
      List<I3NetwComponent> netws = netwRepo.findByNetCadastralInfo(property.propCadnum);
      if (netws.size() > 0) {
         logger.info("ok netw cad: {}", property.propName);
         Long landId = netws.get(0).getLndLandComponentId();
         Optional<I3LandComponent> optLand = landRepo.findById(landId);
         if (optLand.isPresent()) {
            return optLand.get().getId();
         }
      }

      Map<String, List<I3Object>> sbjObjs = subjectsObj.getOrDefault(sbjId, objRepo.findByRtnSbj(sbjId)
      .stream().collect(Collectors.groupingBy(o -> o.getObjDescription().toLowerCase().trim())));
      
      if (sbjObjs.containsKey(property.propName.toLowerCase())) {
         logger.info("ok sbj: {}", property.propName);
         return sbjObjs.get(property.propName.toLowerCase()).get(0).getId();
      }

      if (allObj.containsKey(property.propName.toLowerCase())) {
         logger.warn("ok all: {}", property.propName);
         return allObj.get(property.propName.toLowerCase()).get(0);
      }

      logger.error("no: {}", property.propName);

      return -1L;
   }

   private Long getSbjId(SheetInfo sheet) {
      List<I3CmpyComponent> cmpy = cmpyRepo.findByCmpInn(sheet.inn);
      if (cmpy.size() > 0) {
         return cmpy.get(0).getSbjSubjectId();
      }

      List<I3PersComponent> pers = persRepo.findByPrsItn(sheet.inn);
      if (pers.size() > 0) {
         return pers.get(0).getSbjSubjectId();
      }

      List<I3Subject> sbj = sbjRepo.findBySbjDescription(sheet.subject);
      if (sbj.size() > 0) {
         return sbj.get(0).getId();
      }
      logger.error("Не удалось найти субъекта: {}", sheet.subject);
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