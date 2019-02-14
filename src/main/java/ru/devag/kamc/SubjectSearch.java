package ru.devag.kamc;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import ru.devag.kamc.model.*;
import ru.devag.kamc.repo.*;

@Service
public class SubjectSearch {
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
   
   public Long getLandlordId() {
      List<I3Subject> landlordList = sbjRepo.findBySbjDescriptionIgnoreCaseContaining("Управление экономического развития");
      if (landlordList.size() != 1) {
         logger.error("Не найден арендодатель");
         return -1L;
      }
      return landlordList.get(0).getId();
   }

   public Set<Long> getSbjObjects(Long sbjId) {
      return subjectsObjId.computeIfAbsent(sbjId, k -> 
         objRepo.findByRtnSbj(sbjId).stream()
            .map(obj -> obj.getId()).collect(Collectors.toSet()));
   }


   public Long getSbjId(SheetInfo sheet) {
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

}
