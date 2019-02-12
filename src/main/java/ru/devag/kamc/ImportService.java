package ru.devag.kamc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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

   Map<Long, Set<Long>> subjectsObjId = null;

   Map<Long, I3Object> allObj = null;
   Map<String, Set<Long>> allNames = null;
   Map<String, Set<Long>> allNamesNoSpace = null;
   Map<Double, Set<Long>> allCost = null;
   
   Map<String, Stack<Long>> aprmCadObjIds = null;
   Map<String, Stack<Long>> netwCadObjIds = null;

   Map<String, List<Long>> aprmAddrObjIds = null;
   Map<String, List<Long>> netwAddrObjIds = null;

   Set<Long> foundIds = null;

   private ConcurrentHashMap<String, BookInfo> cache = new ConcurrentHashMap<>();

   private Long landlordId = -1L;
   private Long lptyCatId = -1L;
   private Long lptyClfId = -1L;

   public void initConstants() {
      I3Category cat = catRepo.findByCatCode("LPTY");
      lptyCatId = cat.getId();

      I3ClassifierValue clfLpty = clfRepo.findByCfvCode("CLF_CNTRTYPE_LEA_NF");
      lptyClfId = clfLpty.getId();

      List<I3Subject> landlordList = sbjRepo.findBySbjDescriptionIgnoreCaseContaining("Управление экономического развития");
      if (landlordList.size() != 1) {
         logger.error("Не найден арендодатель");
      }
      landlordId = landlordList.get(0).getId();

      aprmCadObjIds = new HashMap<>();
      aprmRepo.findByApmCadastralInfoNotNull().forEach(aprm -> {
         aprmCadObjIds.computeIfAbsent(aprm.getApmCadastralInfo().trim(), k -> new Stack<>())
            .push(aprm.getObject().getId());
      });

      aprmAddrObjIds = new HashMap<>();
      aprmRepo.findAllAddresses().stream()
         .filter(tuple -> !StringUtils.isEmpty(tuple.get(1, String.class)))
         .forEach(tuple -> {
            aprmAddrObjIds.computeIfAbsent(tuple.get(1, String.class).toLowerCase().trim(), k -> new ArrayList<>())
            .add(tuple.get(0, BigDecimal.class).longValue());
         });
      
      netwCadObjIds = new HashMap<>();
      netwRepo.findObjectsNetCadastralInfoNotNull().forEach(tuple -> {
         netwCadObjIds.computeIfAbsent(tuple.get(0, I3NetwComponent.class).getNetCadastralInfo().trim(), k -> new Stack<>())
            .push(tuple.get(1, I3Object.class).getId());
      });

      netwAddrObjIds = new HashMap<>();
      netwRepo.findAllAddresses().stream()
         .filter(tuple -> !StringUtils.isEmpty(tuple.get(1, String.class)))
         .forEach(tuple -> {
            netwAddrObjIds.computeIfAbsent(tuple.get(1, String.class).toLowerCase().trim(), k -> new ArrayList<>())
            .add(tuple.get(0, BigDecimal.class).longValue());
         });
      
      fillAllNames();
      subjectsObjId = new HashMap<>();
      foundIds = new HashSet<>();

      allCost = null;
   }

   public void put(String code, BookInfo bookInfo) {
      cache.put(code, bookInfo);
   }

   public BookInfo get(String code) {
      return cache.get(code);
   }

   @Transactional
   public void importSheet(SheetInfo sheet, boolean ignoreCheap, boolean ignoreAll, List<String> ignored) {
      Long sbjId = getSbjId(sheet);
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
            if (property.propCost == null && ignoreAll ||
                  property.propCost != null && 
                     (property.propCost < 50000 && (ignoreCheap || ignoreAll) ||
                     property.propCost >= 50000 && ignoreAll)) {
               ignored.add(sheet.cntrNum + ": " + property.propName);
               //logger.warn("ignore: {}", property.propName);
            } else {
               logger.error("not found: {}", property.propName);
               throw new RuntimeException("Не найден хотя бы один объект");
            }
         }
      }

   }

   private Long getObjId(PropertyInfo property, Long sbjId) {
      Long objIdByCadnum = findObjByCadnum(property);
      if (objIdByCadnum > 0) {
         foundIds.add(objIdByCadnum);
         return objIdByCadnum;
      }
      
      Set<Long> sbjObjIds = subjectsObjId.computeIfAbsent(sbjId, k -> 
         objRepo.findByRtnSbj(sbjId).stream().map(obj -> obj.getId()).collect(Collectors.toSet()));

      Long objIdByCost = findObjByCost(property, sbjObjIds);
      if (objIdByCost > 0)
         return objIdByCost;

      Long objIdByName = findObjByName(property, true, sbjObjIds);
      if (objIdByName > 0)
         return objIdByName;

      Long objIdByAddress = findObjByAddress(property);
      if (objIdByAddress > 0) {
         return objIdByAddress;
      }

      objIdByName = findObjByName(property, false, sbjObjIds);
      if (objIdByName > 0)
         return objIdByName;

      return -1L;
   }

   private Long findObjByCadnum(PropertyInfo property) {
      if (property.propCadnum != null) {
         if (property.propType == PropType.APRM || property.propType != PropType.NETW) {
            Stack<Long> aprms = aprmCadObjIds.get(property.propCadnum);
            if (aprms != null && !aprms.empty()) {
               logger.info("ok кад помещ: {}", property.propName);
               return aprms.pop();
            }
         } 
         if (property.propType == PropType.NETW || property.propType != PropType.APRM) {
            Stack<Long> netws = netwCadObjIds.get(property.propCadnum);
            if (netws != null && !netws.empty()) {
               logger.info("ok кад сети: {}", property.propName);
               return netws.pop();
            }
         }
      }
      return -1L;
   }
   
   private Long findObjByCost(PropertyInfo property, Set<Long> sbjObjIds) {
      if (property.propCost != null && property.propCost > 10) {
         Set<Long> allObjIdByCost = getAllCost().get(property.propCost);

         if (allObjIdByCost != null && allObjIdByCost.size() > 0) {
            Long objId;

            List<Long> sbjObjIdByCost = sbjObjIds.stream().parallel()
               .filter(id -> allObjIdByCost.contains(id)).collect(Collectors.toList());

            if (sbjObjIdByCost.size() > 0) {
               objId = getClosestByName(sbjObjIdByCost, property.propName);
               if (objId > 0) {
                  logger.info("ok цена (суб): {}; [{}; {}]", property.propCost, property.propName, allObjIdByCost.size());
                  sbjObjIds.remove(objId);
               }
            } else {
               objId = getClosestByName(allObjIdByCost, property.propName);
               if (objId > 0) {
                  logger.info("ok цена (все): {} [{}; {}]", property.propName, property.propCost, allObjIdByCost.size());
               }
            }
            if (objId > 0) {
               allObjIdByCost.remove(objId);
            }
            return objId;
         }

      }
      return -1L;
   }

   private Long findObjByName(PropertyInfo property, boolean withSpaces, Set<Long> sbjObjIds) {
      String name = withSpaces ? property.propName.toLowerCase() : property.propName.toLowerCase().replaceAll("\\s", ""); 
      
      Set<Long> objIdByName = (withSpaces ? allNames : allNamesNoSpace).get(name);
      if (objIdByName != null && objIdByName.size() > 0) {
         Long objId;

         Optional<Long> sbjObjByName = sbjObjIds.stream().parallel()
            .filter(id -> objIdByName.contains(id)).findAny();

         if (sbjObjByName.isPresent()) {
            logger.info("ok имя (суб): {} [{} шт.]", property.propName, objIdByName.size());
            objId = sbjObjByName.get();
            sbjObjIds.remove(objId);
         } else {
            logger.info("ok имя (все): {} [{} шт.]", property.propName, objIdByName.size());
            objId = objIdByName.iterator().next();
         }
         objIdByName.remove(objId);
         return objId;
      }
      
      return -1L;
   }

   //LevenshteinDistance lv = new LevenshteinDistance();
   //System.out.println(lv.apply("жопа", "рама"));

   //System.out.println("lev: " + lv.apply(s1, s2));
   //return 1 - ((double) lv.apply(s1, s2)) / Math.max(s1.length(), s2.length());

   private Long findObjByAddress(PropertyInfo property) {
      if (property.propAddress != null) {
         if (property.propType == PropType.NETW || property.propType != PropType.APRM) {
            List<Long> netws = netwAddrObjIds.get(property.propAddress.toLowerCase());
            if (netws != null && netws.size() > 0) {
               /*if (property.propAddress.contains("ийп")) {
                  int i = 1;
               }*/
   
               Long objId = getClosestByName(netws, property.propName);
               if (objId > 0) {
                  logger.info("ok адрес (сети): {} [{}]", property.propName, property.propAddress);
                  netws.remove(objId);
                  return objId;
               }
            }
         }
         if (property.propType == PropType.APRM || property.propType != PropType.NETW) {
            List<Long> aprms = aprmAddrObjIds.get(property.propAddress.toLowerCase());
            if (aprms != null && aprms.size() > 0) {
               Long objId = getClosestByName(aprms, property.propName);
               if (objId > 0) {
                  logger.info("ok адрес (помещ): {} [{}]", property.propName, property.propAddress);
                  aprms.remove(objId);
                  return objId;
               }
            }
         } 
      }

      return -1L;
   }

   private Long getClosestByName(Collection<Long> ids, String objName) {
      I3Comparator comparator = new I3Comparator(objName);

      int thresh = 10;

      if (ids.size() == 1) {
         I3Object obj = allObj.get(ids.iterator().next());
         if (obj != null && comparator.getDistance(obj) < thresh) {
            return obj.getId();
         }
      } else {
         Optional<I3Object> optObj = ids.stream()
            .map(id -> allObj.get(id))
            .sorted(comparator)
            .findFirst();

         if (optObj.isPresent() && comparator.getDistance(optObj.get()) < thresh) {
            return optObj.get().getId();
         }

      }
      return -1L;

   }

   private void fillAllNames() {
      allObj = new HashMap<>();
      allNames = new HashMap<>();
      allNamesNoSpace = new HashMap<>();
      logger.debug("Чтение описаний всех объектов. Начало");

      Iterable<I3Object> it = objRepo.findAll();
      for (I3Object o: it) {
         String descr = o.getObjDescription();
         if (descr != null) {
            allObj.put(o.getId(), o);

            allNames.computeIfAbsent(descr.toLowerCase().trim(), f -> new HashSet<>())
               .add(o.getId());
               
            allNamesNoSpace.computeIfAbsent(descr.toLowerCase().replaceAll("\\s", ""), f -> new HashSet<>())
               .add(o.getId());
         }
      }

      logger.debug("Чтение описаний всех объектов. Окончание");
   }

   private Map<Double, Set<Long>> getAllCost() {
      if (allCost == null) {
         logger.info("Чтение стоимостей всех объектов. Начало");

         allCost = new HashMap<>();
         objRepo.getAllLastCost().stream()
         .parallel()
         .filter(item -> item.get(1, BigDecimal.class) != null)
         .forEach(item -> {
            allCost.computeIfAbsent(item.get(1, BigDecimal.class).doubleValue(), k -> new HashSet<>())
               .add(item.get(0, BigDecimal.class).longValue());
         });
         logger.info("Чтение стоимостей всех объектов. Окончание");
      }
      return allCost;
   }
   
   /*private Set<Long> getObjByCost(PropertyInfo property) {
      return getAllCost().stream()
         .parallel()
         .filter(item -> item.get(1, BigDecimal.class) != null && 
            item.get(1, BigDecimal.class).doubleValue() == property.propCost &&
            (item.get(2, String.class)).equals(property.propType.toString()))
         .map(item -> (item.get(0, BigDecimal.class)).longValue())
         .collect(Collectors.toSet());
   }*/

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

   class I3Comparator implements Comparator<I3Object> {
      private String value;

      public I3Comparator(String value) {
         this.value = value.toLowerCase();
      }

      public Integer getDistance(I3Object obj) {
         LevenshteinDistance lv = new LevenshteinDistance();
         int dist = lv.apply(obj.getObjDescription().toLowerCase().trim(), value);
         return dist;
      }

      @Override
      public int compare(I3Object o1, I3Object o2) {
         return getDistance(o1).compareTo(getDistance(o2));
      }
   }

   
}