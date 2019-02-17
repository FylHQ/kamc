package ru.devag.kamc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import ru.devag.kamc.model.*;
import ru.devag.kamc.repo.*;

@Service
public class RelationUtils {
   private static Logger logger = LoggerFactory.getLogger(ObjectSearch.class);

   private I3RelationRepository rtnRepo;
   private I3TitlComponentRepository titlRepo;
   private I3ClassifierValueRepository clfRepo;
   private I3ObjRtnRepository obrRepo; 
   private I3SbjRtnRepository sbrRepo; 
   private I3RtnBstRepository rbsRepo; 


   private I3Category titlCat;

   public RelationUtils(
         I3RelationRepository rtnRepo, 
         I3TitlComponentRepository titlRepo,
         I3ClassifierValueRepository clfRepo,
         I3CategoryRepository catRepo,
         I3ObjRtnRepository obrRepo,
         I3SbjRtnRepository sbrRepo,
         I3RtnBstRepository rbsRepo) {
      this.rtnRepo = rtnRepo;
      this.titlRepo = titlRepo;
      this.clfRepo = clfRepo;
      this.obrRepo = obrRepo;
      this.sbrRepo = sbrRepo;
      this.rbsRepo = rbsRepo;

      this.titlCat = catRepo.findByCatCode("LPTY");
   }

   public Long create(SheetInfo sheet, PropertyInfo property, String clfCode, String suffix, Long objId, Long sbjId, Long sbrTypeId) {
      I3Relation rtn = new I3Relation();
      rtn.setCatCategoryId(titlCat.getId());
      rtn.setRtnDate(sheet.cntrStartDate);
      rtn.setStsStatusId(2L);
      rtn.setRtnNumber("XLSX_2019_" + suffix + "_" + sheet.cntrNum + "_" + property.propNum);
      rtnRepo.save(rtn);
      
      I3TitlComponent titl = new I3TitlComponent();
      titl.setRelation(rtn);
      titl.setCfvClassifierValueId(clfRepo.findByCfvCode(clfCode).getId());
      titlRepo.save(titl);

      I3SbjRtn sbr = new I3SbjRtn();
      sbr.setRtnRelationId(rtn.getId());
      sbr.setSbjSubjectId(sbjId);
      sbr.setSbrType(sbrTypeId);
      sbrRepo.save(sbr);

      I3ObjRtn obr = new I3ObjRtn();
      obr.setObjObjectId(objId);
      obr.setRtnRelationId(rtn.getId());
      obr.setObrType(2302L);
      obrRepo.save(obr);

      return rtn.getId();
   }
   
   public void createRent(boolean checkExists, SheetInfo sheet, PropertyInfo property, Long objId, Long sbjId, Long bstId) {
      Long rtnId = -1L;
      if (checkExists) {
         List<I3ObjRtn> rtns = obrRepo.findActualRentByObjId(objId);
         if (rtns.size() > 0) {
            if (rtns.size() > 1) {
               logger.warn("Найдено более одного права аренды: {} шт.", rtns.size());
            }
            rtnId = rtns.get(0).getRtnRelationId();
         }
      }

      if (rtnId < 0) {
         rtnId = create(sheet, property, "TITTYPE_LEASE", "RENT", objId, sbjId, 4305L);
      }
      
      I3RtnBst rbs = new I3RtnBst();
      rbs.setRtnRelationId(rtnId);
      rbs.setBstBasementId(bstId);
      rbs.setRbsType(3200L);
      rbsRepo.save(rbs);
   }

   public void createMS(SheetInfo sheet, PropertyInfo property, Long objId, Long sbjId) {
      create(sheet, property, "TITYPE_TITLE_MUNIC", "MS", objId, sbjId, 4324L);
   }

   public void createMK(SheetInfo sheet, PropertyInfo property, Long objId, Long sbjId) {
      create(sheet, property, "CLF_TITTYPE_MUNKAZ", "MK", objId, sbjId, 4303L);
   }

}