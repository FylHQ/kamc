package ru.devag.kamc;

import java.util.concurrent.ConcurrentHashMap;

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

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
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

   Map<Long, List<I3Object>> subjectsObjId = new HashMap<>();
   Map<Long, Map<String, List<I3Object>>> subjectsObj = new HashMap<>();
   //Map<String, List<I3Object>> allObj = null;
   Map<String, Set<Long>> allObj = null;
   List<Object[]> allCost = null;
   
   Map<String, Stack<Long>> aprmCadObjIds = new HashMap<>();
   Map<String, Stack<Long>> netwCadObjIds = new HashMap<>();

   private ConcurrentHashMap<String, BookInfo> cache = new ConcurrentHashMap<>();

   private Long landlordId = -1L;
   private Long lptyCatId = -1L;
   private Long lptyClfId = -1L;

   public void initConstants() {
      if (lptyCatId > 0)
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

      aprmRepo.findByApmCadastralInfoNotNull().forEach(aprm -> {
         aprmCadObjIds.computeIfAbsent(aprm.getApmCadastralInfo(), k -> new Stack<>())
            .push(aprm.getObject().getId());
      });

      
      netwRepo.findObjectsNetCadastralInfoNotNull().forEach(tuple -> {
         netwCadObjIds.computeIfAbsent(tuple.get(0, I3NetwComponent.class).getNetCadastralInfo(), k -> new Stack<>())
            .push(tuple.get(1, I3Object.class).getId());
      });
   }


   public void put(String code, BookInfo bookInfo) {
      cache.put(code, bookInfo);
   }

   public BookInfo get(String code) {
      return cache.get(code);
   }

   @Transactional
   public void importSheet(SheetInfo sheet, boolean ignoreCheap, boolean ignoreAll, List<String> ignored) {
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

         if (objId != null && objId > 0) {
            I3ObjBst objBst = new I3ObjBst();
            objBst.setObbType(2105L);
            objBst.setObjObjectId(objId);
            objBst.setBstBasementId(bst.getId());
            objBstRepo.save(objBst);
         } else {
            if (property.propCost == null && ignoreAll ||
                  property.propCost != null && 
                     (property.propCost < 50000 && (ignoreCheap || ignoreAll) ||
                     property.propCost >= 50000 && ignoreAll)) {
               ignored.add(sheet.cntrNum + ": " + property.propName);
               logger.warn("ignore: {}", property.propName);
            } else {
               logger.error("not found: {}", property.propName);
               throw new RuntimeException("Не найден хотя бы один объект");
            }
         }


         //objRepo.findByObjDescriptionIgnoreCase(property.propName);
      }

   }

   private Long getObjId(PropertyInfo property, Long sbjId) {
      if (property.propCadnum != null) {
         if (property.propType == PropType.APRM) {
            Stack<Long> aprms = aprmCadObjIds.get(property.propCadnum);
            if (aprms != null && !aprms.empty()) {
               logger.info("ok aprm cad: {}", property.propName);
               return aprms.pop();
            }
         }
         
         if (property.propType == PropType.NETW) {
            Stack<Long> netws = netwCadObjIds.get(property.propCadnum);
            if (netws != null && !netws.empty()) {
               logger.info("ok netw cad: {}", property.propName);
               return netws.pop();
            }
         }
      }

      List<I3Object> sbjObjIds = subjectsObjId.computeIfAbsent(sbjId, f -> objRepo.findByRtnSbj(sbjId));
      //Map<String, List<I3Object>> sbjObjProps = subjectsObj.getOrDefault(sbjId, sbjObjIds
      //.stream().collect(Collectors.groupingBy(o -> o.getObjDescription().toLowerCase().trim())));

      if (property.propCost != null) {
         Set<Long> allObjByCost = getObjByCost(property);
         if (allObjByCost.size() == 1) {
            logger.info("ok by all cost: {} [{}]", property.propName, property.propCost);
            return allObjByCost.iterator().next();
         } else if (allObjByCost.size() > 1) {
            List<I3Object> sbjObjByCost = sbjObjIds.stream().parallel()
            .filter(obj -> allObjByCost.contains(obj.getId())).collect(Collectors.toList());

            if (sbjObjByCost.size() == 1) {
               logger.info("ok by cost: {} [{}]", property.propName, property.propCost);
               return sbjObjByCost.get(0).getId();
            }
            logger.warn("multiple by cost: {} [{}]", allObjByCost.size(), property.propCost);
         }
      }

      
      /*Optional<I3Object> objBySbjRtn = sbjObjIds.stream().parallel()
         .filter(obj -> obj.getObjDescription() != null && 
         obj.getObjDescription().toLowerCase().trim().equalsIgnoreCase(property.propName.toLowerCase()))
         .findAny();
      
      if (objBySbjRtn.isPresent()) {
         logger.info("ok by sbj: {}", property.propName);
         return objBySbjRtn.get().getId();
      }*/
      /*if (sbjObjProps.containsKey(property.propName.toLowerCase())) {
         logger.info("ok sbj: {}", property.propName);
         return sbjObjProps.get(property.propName.toLowerCase()).get(0).getId();
      }*/

      

      Set<Long> allObjByName = getAllObj().get(property.propName.toLowerCase());
      if (allObjByName != null) {
         if (allObjByName.size() == 1) {
            logger.info("ok by all name: {}", property.propName);   
            return allObjByName.iterator().next();
         } else if (allObjByName.size() > 1) {
            List<I3Object> sbjObjByName = sbjObjIds.stream().parallel()
            .filter(obj -> allObjByName.contains(obj.getId())).collect(Collectors.toList());
            if (sbjObjByName.size() == 1) {
               logger.info("ok by name: {}", property.propName);
               return sbjObjByName.get(0).getId();
            }
            logger.warn("multiple by name: {}", property.propName);
         }
      }

      return -1L;
   }

   private Map<String, Set<Long>> getAllObj() {
      if (allObj == null) {
         logger.info("Чтение описаний всех объектов. Начало");
         allObj = new HashMap<>();
         Iterable<I3Object> it = objRepo.findAll();
         logger.info("Чтение описаний всех объектов. Окончание");
         for (I3Object o: it) {
            String descr = o.getObjDescription();
            if (descr != null) {
               allObj.computeIfAbsent(descr.toLowerCase().trim(), f -> new HashSet<>())
                  .add(o.getId());
            }
         }
      }

      return allObj;
   }

   private List<Object[]> getAllCost() {
      if (allCost == null) {
         logger.info("Чтение стоимостей всех объектов. Начало");
         allCost = objRepo.getAllLastCost();
         logger.info("Чтение стоимостей всех объектов. Окончание");
      }
      return allCost;
   }
   
   private Set<Long> getObjByCost(PropertyInfo property) {
      return getAllCost().stream()
      .parallel()
      .filter(item -> item[1] != null && 
         ((BigDecimal)item[1]).doubleValue() == property.propCost &&
         ((String)item[2]).equals(property.propType.toString()))
      .map(item -> ((BigDecimal)item[0]).longValue())
      .collect(Collectors.toSet());
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