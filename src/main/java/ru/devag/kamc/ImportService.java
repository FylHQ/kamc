package ru.devag.kamc;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ru.devag.kamc.model.*;
import ru.devag.kamc.repo.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ImportService {
   private static Logger logger = LoggerFactory.getLogger(ImportService.class);

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

   private ConcurrentHashMap<String, BookInfo> cache = new ConcurrentHashMap<>();

   public void put(String code, BookInfo bookInfo) {
      cache.put(code, bookInfo);
   }

   public BookInfo get(String code) {
      return cache.get(code);
   }

   /*
    * private I3Subject createSubject(SheetInfo sheet) {
    * 
    * }
    */

   @Async
   @Transactional
   public CompletableFuture<String> importSheets(List<SheetInfo> sheets) throws InterruptedException {
      I3Category cat = catRepo.findByCatCode("LPTY");
      I3ClassifierValue clfLpty = clfRepo.findByCfvCode("CLF_CNTRTYPE_LEA_NF");
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
      List<I3Subject> landlordList = sbjRepo.findBySbjDescriptionIgnoreCaseContaining("Управление экономического развития");
      if (landlordList.size() != 1) {
         logger.error("Не найден арендодатель");
         return CompletableFuture.completedFuture("ERROR");
      }
      Long landlordId = landlordList.get(0).getId();
                  
      
      for (SheetInfo sheet : sheets) {
         if (StringUtils.isEmpty(sheet.inn)) {
            logger.info("NO INN sbj: {}", sheet.subject);
         } else {
            Long sbjId = getSbjId(sheet);
            if (sbjId > 0) {
               I3Basement bst = new I3Basement();
               I3LptyComponent lpty = new I3LptyComponent();

               bst.setBstNumber(sheet.cntrNum);
               bst.setCatCategoryId(cat.getId());
               bst.setStsStatusId(1L);


               
               lpty.setLpyIsOtherRate("F");
               lpty.setCfvClassifierValueId(clfLpty.getId());

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
               logger.info("{}", bst.getId());
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

            }
            

         }
      }
      return CompletableFuture.completedFuture("OK");
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
      logger.error("Sbj not found: {}", sheet.subject);
      return -1L;
   }
}