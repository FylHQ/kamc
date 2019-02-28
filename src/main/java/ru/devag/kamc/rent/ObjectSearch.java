package ru.devag.kamc.rent;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import ru.devag.kamc.rent.PropertyInfo.PropType;
import ru.devag.kamc.model.*;
import ru.devag.kamc.repo.*;

@Service
public class ObjectSearch {
   private static Logger logger = LoggerFactory.getLogger(ObjectSearch.class);

   @Autowired
   I3ObjectRepository objRepo;

   @Autowired
   I3AprmComponentRepository aprmRepo;

   @Autowired
   I3NetwComponentRepository netwRepo;

   public int threshFull; 
   public int threshShort;

   Map<Long, I3Object> allObj = null;
   Map<String, List<Long>> allNames = null;
   Map<String, List<Long>> allNamesNoSpace = null;
   Map<Double, List<Long>> allCost = null;
   Map<Double, List<Long>> allLength = null;
   
   Map<String, Stack<Long>> aprmCadObjIds = null;
   Map<String, Stack<Long>> netwCadObjIds = null;

   Map<String, List<Long>> aprmAddrObjIds = null;
   Map<String, List<Long>> netwAddrObjIds = null;

   public void init(int threshFull) {
      this.threshFull = threshFull;
      this.threshShort = Math.round(threshFull / 3);
      
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

      allCost = null;
      allLength = null;
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

            allNames.computeIfAbsent(descr.toLowerCase().trim(), f -> new ArrayList<>())
               .add(o.getId());
               
            allNamesNoSpace.computeIfAbsent(descr.toLowerCase().replaceAll("\\s", ""), f -> new ArrayList<>())
               .add(o.getId());
         }
      }

      logger.debug("Чтение описаний всех объектов. Окончание");
   }


   private Map<Double, List<Long>> getAllCost() {
      if (allCost == null) {
         logger.debug("Чтение стоимостей всех объектов. Начало");

         allCost = new HashMap<>();
         objRepo.getAllLastCost().stream()
            .filter(item -> item.get(1, BigDecimal.class) != null)
            .forEach(item -> {
               allCost.computeIfAbsent(item.get(1, BigDecimal.class).doubleValue(), k -> new ArrayList<>())
                  .add(item.get(0, BigDecimal.class).longValue());
             });
         logger.debug("Чтение стоимостей всех объектов. Окончание");
      }
      return allCost;
   }
   
   private Map<Double, List<Long>> getAllLength() {
      if (allLength == null) {
         logger.debug("Чтение длин сетей. Начало");

         allLength = new HashMap<>();
         netwRepo.findAllLength().stream()
            .forEach(tuple -> {
               allLength.computeIfAbsent(tuple.get(1, BigDecimal.class).doubleValue(), k -> new ArrayList<>())
               .add(tuple.get(0, BigDecimal.class).longValue());
            });
         logger.debug("Чтение длин сетей. Окончание");
      }
      return allLength;
   }
   
   public Long findObjByCadnum(PropertyInfo property) {
      if (property.propCadnum != null) {
         if (property.propType == PropType.APRM || property.propType != PropType.NETW) {
            Stack<Long> aprms = aprmCadObjIds.get(property.propCadnum);
            if (aprms != null && !aprms.empty()) {
               logger.info("ok кад помещ [{}] {}", property.propNum, property.propName);
               return aprms.pop();
            }
         } 
         if (property.propType == PropType.NETW || property.propType != PropType.APRM) {
            Stack<Long> netws = netwCadObjIds.get(property.propCadnum);
            if (netws != null && !netws.empty()) {
               logger.info("ok кад сети [{}] {}", property.propNum, property.propName);
               return netws.pop();
            }
         }
      }
      return -1L;
   }
   
   public Long findObjByCost(PropertyInfo property, Set<Long> sbjObjIds) {
      if (property.propCost != null && property.propCost > 10) {
         List<Long> allObjIdByCost = getAllCost().get(property.propCost);

         /*if (property.propNum == 207) {
            int i = 1;
         }*/
         
         if (allObjIdByCost != null && allObjIdByCost.size() > 0) {
            Long objId = -1L;

            List<Long> sbjObjIdByCost = sbjObjIds.stream().parallel()
               .filter(id -> allObjIdByCost.contains(id)).collect(Collectors.toList());

            if (sbjObjIdByCost.size() > 0) {
               objId = getClosestByNameTwoPhase(sbjObjIdByCost, property.propName);
            }

            if (objId > 0) {
               logger.info("ok цена (суб) [{}] {}; [{}; {}]", property.propNum, property.propCost, property.propName, sbjObjIdByCost.size());
               sbjObjIds.remove(objId);
            } else {
               objId = getClosestByNameTwoPhase(allObjIdByCost, property.propName);
               if (objId > 0) {
                  logger.info("ok цена (все) [{}] {} [{}; {}]", property.propNum, property.propCost, property.propName, allObjIdByCost.size());
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

   public Long findObjByLength(PropertyInfo property, Set<Long> sbjObjIds) {
      if (property.propLength != null) {
         List<Long> allObjIdByLength = getAllLength().get(property.propLength);

         if (allObjIdByLength != null && allObjIdByLength.size() > 0) {
            Long objId = -1L;

            List<Long> sbjObjIdByLength = sbjObjIds.stream().parallel()
               .filter(id -> allObjIdByLength.contains(id)).collect(Collectors.toList());

            if (sbjObjIdByLength.size() > 0) {
               objId = getClosestByNameTwoPhase(sbjObjIdByLength, property.propName);
            }

            if (objId > 0) {
               logger.info("ok длина (суб) [{}] {}; [{}; {}]", property.propNum, property.propLength, property.propName, sbjObjIdByLength.size());
               sbjObjIds.remove(objId);
            } else {
               objId = getClosestByNameTwoPhase(allObjIdByLength, property.propName);
               if (objId > 0) {
                  logger.info("ok длина (все) [{}] {} [{}; {}]", property.propNum, property.propLength, property.propName, allObjIdByLength.size());
               }
            }
            
            if (objId > 0) {
               allObjIdByLength.remove(objId);
            }
            return objId;
         }

      }
      return -1L;
   }

   public Long findObjByName(PropertyInfo property, boolean withSpaces, Set<Long> sbjObjIds) {
      String name = withSpaces ? property.propName.toLowerCase() : property.propName.toLowerCase().replaceAll("\\s", ""); 
      
      List<Long> objIdByName = (withSpaces ? allNames : allNamesNoSpace).get(name);
      if (objIdByName != null && objIdByName.size() > 0) {
         Long objId = -1L;

         Optional<Long> sbjObjByName = sbjObjIds.stream().parallel()
            .filter(id -> objIdByName.contains(id)).findAny();

         if (sbjObjByName.isPresent()) {
            logger.info("ok имя (суб) [{}] {} [{} шт.]", property.propNum, property.propName, objIdByName.size());
            objId = sbjObjByName.get();
            sbjObjIds.remove(objId);
         } else {
            if (property.propType == PropType.APRM || 
               property.propType == PropType.NETW ||
               objIdByName.size() < 5) {

               logger.warn("ok имя (все) [{}] {} [{} шт.]", property.propNum, property.propName, objIdByName.size());
               objId = objIdByName.get(0);
            } else {
               logger.warn("ignore имя (все) [{}] {} [{} шт.]", property.propNum, property.propName, objIdByName.size());
            }
         }

         if (objId > 0) {
            objIdByName.remove(objId);
         }
         return objId;
      }
      
      return -1L;
   }

   public Long findObjByAddress(PropertyInfo property) {
      if (property.propAddress != null) {
         if (property.propType == PropType.NETW || property.propType != PropType.APRM) {
            List<Long> netws = netwAddrObjIds.get(property.propAddress.toLowerCase());
            if (netws != null && netws.size() > 0) {
               Long objId = getClosestByNameTwoPhase(netws, property.propName);
               if (objId < 0) {
                  objId = getClosestByName(netws, property.propName, false, 40);
               }
               if (objId > 0) {
                  logger.info("ok адрес (сети) [{}] {} [{}]", property.propNum, property.propName, property.propAddress);
                  netws.remove(objId);
                  return objId;
               }
            }
         }
         if (property.propType == PropType.APRM || property.propType != PropType.NETW) {
            List<Long> aprms = aprmAddrObjIds.get(property.propAddress.toLowerCase());
            if (aprms != null && aprms.size() > 0) {
               Long objId = getClosestByNameTwoPhase(aprms, property.propName);
               if (objId > 0) {
                  logger.info("ok адрес (помещ) [{}] {} [{}]", property.propNum, property.propName, property.propAddress);
                  aprms.remove(objId);
                  return objId;
               }
            }
         } 
      }

      return -1L;
   }

   private Long getClosestByNameTwoPhase(List<Long> ids, String objName) {
      Long objId = getClosestByName(ids, objName, false, threshFull);
      if (objId < 0) {
         objId = getClosestByName(ids, objName, true, threshShort);
      }
      return objId;
   }

   private Long getClosestByName(List<Long> ids, String objName, boolean trimToSrcLength, int threshold) {
      I3Comparator comparator = new I3Comparator(objName, trimToSrcLength);

      if (ids.size() == 1) {
         I3Object obj = allObj.get(ids.get(0));
         if (obj != null && comparator.getDistance(obj) < threshold) {
            return obj.getId();
         }
      } else {
         Optional<I3Object> optObj = ids.stream()
            .map(id -> allObj.get(id))
            .sorted(comparator)
            .findFirst();

         if (optObj.isPresent() && comparator.getDistance(optObj.get()) < threshold) {
            return optObj.get().getId();
         }

      }
      return -1L;

   }
     
   class I3Comparator implements Comparator<I3Object> {
      private String value;
      private boolean trimToSrcLength;

      public I3Comparator(String value, boolean trimToSrcLength) {
         this.value = value.toLowerCase();
         this.trimToSrcLength = trimToSrcLength;
      }

      public Integer getDistance(I3Object obj) {
         LevenshteinDistance lv = new LevenshteinDistance();
         String compared = obj.getObjDescription().toLowerCase().trim();
         int dist;
         if (trimToSrcLength)
            dist = lv.apply(StringUtils.substring(compared, 0, value.length()), value);
         else
            dist = lv.apply(compared, value);
         return dist;
      }

      @Override
      public int compare(I3Object o1, I3Object o2) {
         return getDistance(o1).compareTo(getDistance(o2));
      }
   }

}
