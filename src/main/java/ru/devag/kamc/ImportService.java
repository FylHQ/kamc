package ru.devag.kamc;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ru.devag.kamc.model.*;
import ru.devag.kamc.repo.*;

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

   private ConcurrentHashMap<String, BookInfo> cache = new ConcurrentHashMap<>();

   public void put(String code, BookInfo bookInfo) {
      cache.put(code, bookInfo);
   }
   
   public BookInfo get(String code) {
      return cache.get(code);
   }

   /*private I3Subject createSubject(SheetInfo sheet) {

   }*/

   @Async
   public CompletableFuture<String> importSheets(List<SheetInfo> sheets) throws InterruptedException {
      for (SheetInfo sheet: sheets) {
         if (StringUtils.isEmpty(sheet.inn)) {
            logger.info("NO INN sbj: {}", sheet.subject);
         } else {
            List<I3CmpyComponent> cmpy = cmpyRepo.findByCmpInn(sheet.inn);
            if (cmpy.size() == 0) {
               List<I3PersComponent> pers = persRepo.findByPrsItn(sheet.inn);
               if (pers.size() == 0) {
                  List<I3Subject> sbj = sbjRepo.findBySbjDescription(sheet.subject);
                  if (sbj.size() == 0) {
                     logger.info("sbj: {}", sheet.subject);
                  } else {
                     logger.info("sbj new INN: {}", sheet.inn);
                  }
               }
               //logger.info("cmpy: {}", cmpy.get(0).getT3cmpShortName());
            }
         }
      }
      return CompletableFuture.completedFuture("OK");
   }
}