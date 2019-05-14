package ru.devag.kamc;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import ru.devag.kamc.model.*;
import ru.devag.kamc.nto.NtoItem;
import ru.devag.kamc.repo.*;

@Service
public class SubjectUtils {
   private static Logger logger = LoggerFactory.getLogger(SubjectUtils.class);

   @Autowired
   I3SubjectRepository sbjRepo;

   @Autowired
   I3CmpyComponentRepository cmpyRepo;

   @Autowired
   I3PersComponentRepository persRepo;

   @Autowired
   I3ObjectRepository objRepo;

   @Autowired
   I3CategoryRepository catRepo;

   @Autowired
   I3ExtAttributeValueRepository extvRepo;

   @Autowired
   I3SbjCommunicationRepository sbjcommRepo;

   @Autowired
   I3ClassifierValueRepository clfRepo;

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


   public I3Subject getSbj(String inn, String description) {
      List<I3CmpyComponent> cmpy = cmpyRepo.findByCmpInn(inn);
      if (cmpy.size() > 0) {
         return cmpy.get(0).getSbj();
      }

      List<I3PersComponent> pers = persRepo.findByPrsItn(inn);
      if (pers.size() > 0) {
         return pers.get(0).getSbj();
      }

      List<I3Subject> sbj = sbjRepo.findBySbjDescription(description);
      if (sbj.size() > 0) {
         return sbj.get(0);
      }
      
      return null;
   }

   public I3Subject createSbj(NtoItem item) {
      I3Subject sbj = new I3Subject();
      
      boolean isPers = StringUtils.isEmpty(item.getInn()) || item.getInn().length() == 12;
      I3Category cat = catRepo.findByCatCode(isPers ? "PERS" : "CMPY");
      sbj.setCatCategoryId(cat.getId());
      sbj.setSbjDescription(item.getSbj());
      sbj.setSbjNumber("XLSX_2019_NSTO_SBJ_" + item.getRowId());
      sbj.setStsStatusId(1L);
      
      sbjRepo.save(sbj);

      if (isPers) {
         I3PersComponent pers = new I3PersComponent();
         pers.setSbj(sbj);
         pers.setPrsIstaxfree("F");

         String fio[] = item.getSbj().split("\\s+", 3);
         if (fio.length > 0) {
            pers.setPrsLastName(fio[0]);
            if (fio.length > 1) {
               pers.setPrsFirstName(fio[1]);
               if (fio.length > 2) {
                  pers.setPrsSecondName(fio[2]);
               }
            }
         }
         pers.setPrsItn(item.getInn());
         persRepo.save(pers);
      } else {
         I3CmpyComponent cmpy = new I3CmpyComponent();
         cmpy.setSbj(sbj);
         cmpy.setCmpIstaxfree("F");
         cmpy.setCmpIsBelongToRegistry("F");
         cmpy.setCmpInn(item.getInn());
         String clfCode;
         if (item.getSbj().startsWith("ООО ")) {
            clfCode = "CLF_CMPTYPE_029";
         } else if (item.getSbj().startsWith("ОАО ")) {
            clfCode = "CLF_CMPTYPE_032";
         } else if (item.getSbj().startsWith("ПО ")) {
            clfCode = "CLF_CMPTYPE_035";
         } else if (item.getSbj().startsWith("ЗАО ")) {
            clfCode = "CLF_CMPTYPE_014";
         } else {
            clfCode = null;
         }
         if (clfCode != null) {
            cmpy.setCfvClassifierValueId(clfRepo.findByCfvCode(clfCode).getId());
         }
         cmpyRepo.save(cmpy);
      }

      if (!StringUtils.isEmpty(item.getAddress())) {
         I3ExtAttributeValue extv = new I3ExtAttributeValue();
         extv.setExaExtAttributeId(29594062L);
         extv.setExvComId(sbj.getId());
         extv.setExvText(item.getAddress());         
         extvRepo.save(extv);
      }

      if (!StringUtils.isEmpty(item.getPhone())) {
         I3SbjCommunication sbjComm = new I3SbjCommunication();
         sbjComm.setSbjSubjectId(sbj.getId());
         sbjComm.setSbcTypeCode("PHO");
         sbjComm.setSbcValue(item.getPhone());
         sbjcommRepo.save(sbjComm);
      }

      return sbj;
   }

   

}
