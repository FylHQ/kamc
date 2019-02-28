package ru.devag.kamc.rent;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import ru.devag.kamc.model.*;
import ru.devag.kamc.repo.*;

@Service
public class SubjectUtils {
   private static Logger logger = LoggerFactory.getLogger(ObjectSearch.class);

   @Autowired
   I3SubjectRepository sbjRepo;

   @Autowired
   I3CmpyComponentRepository cmpyRepo;

   @Autowired
   I3PersComponentRepository persRepo;

   @Autowired
   I3ObjectRepository objRepo;

   private Map<Long, Set<Long>> subjectsObjId = null;
   
   public void init() {
      subjectsObjId = new HashMap<>();
   }
   
   public Long getDepSbjId() {
      List<I3Subject> sbjList = sbjRepo.findBySbjDescriptionIgnoreCaseContaining("Управление экономического развития");
      if (sbjList.size() != 1) {
         logger.error("Не найдено УЭР");
         return -1L;
      }
      return sbjList.get(0).getId();
   }

   public Long getPKGOSbjId() {
      Optional<I3Subject> sbj = sbjRepo.findBySbjNumber("LOTUS_647CC78822808A1B4C256A0500765C7B");
      if (!sbj.isPresent()) {
         logger.error("Не удалось найти ПКГО по коду");
         return -1L;
      } else {
         return sbj.get().getId();
      }
   }

   public Set<Long> getSbjObjects(Long sbjId) {
      return subjectsObjId.computeIfAbsent(sbjId, k -> 
         objRepo.findByRtnSbj(sbjId).stream()
            .map(obj -> obj.getId()).collect(Collectors.toSet()));
   }


   public I3Subject getSbj(RentSheet sheet) {
      List<I3CmpyComponent> cmpy = cmpyRepo.findByCmpInn(sheet.inn);
      if (cmpy.size() > 0) {
         return cmpy.get(0).getSbj();
      }

      List<I3PersComponent> pers = persRepo.findByPrsItn(sheet.inn);
      if (pers.size() > 0) {
         return pers.get(0).getSbj();
      }

      List<I3Subject> sbj = sbjRepo.findBySbjDescription(sheet.subject);
      if (sbj.size() > 0) {
         return sbj.get(0);
      }
      logger.error("Не удалось найти субъекта: {}", sheet.subject);
      
      return null;
   }

}
