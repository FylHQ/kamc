package ru.devag.kamc.rent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
   private I3TratComponentRepository tratRepo;
   private I3TratPropertyRepository trapPropRepo;

   private I3Category titlCat;
   private I3Category tratCat;

   public RelationUtils(I3RelationRepository rtnRepo, I3TitlComponentRepository titlRepo,
         I3ClassifierValueRepository clfRepo, I3CategoryRepository catRepo, I3ObjRtnRepository obrRepo,
         I3SbjRtnRepository sbrRepo, I3RtnBstRepository rbsRepo, I3TratComponentRepository tratRepo,
         I3TratPropertyRepository trapPropRepo) {
      this.rtnRepo = rtnRepo;
      this.titlRepo = titlRepo;
      this.clfRepo = clfRepo;
      this.obrRepo = obrRepo;
      this.sbrRepo = sbrRepo;
      this.rbsRepo = rbsRepo;
      this.tratRepo = tratRepo;
      this.trapPropRepo = trapPropRepo;

      this.titlCat = catRepo.findByCatCode("TITL");
      this.tratCat = catRepo.findByCatCode("TRAT");
   }

   public Long create(RentSheet sheet, PropertyInfo property, String clfCode, String suffix, Long objId, Long sbjId,
         Long sbrTypeId) {
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

   public void createRent(boolean checkExists, RentSheet sheet, PropertyInfo property, Long objId, Long sbjId,
         Long bstId) {
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

   public Long createMS(RentSheet sheet, PropertyInfo property, Long objId, Long sbjId) {
      return create(sheet, property, "TITYPE_TITLE_MUNIC", "MS", objId, sbjId, 4324L);
   }

   public Long createMK(RentSheet sheet, PropertyInfo property, Long objId, Long sbjId) {
      return create(sheet, property, "CLF_TITTYPE_MUNKAZ", "MK", objId, sbjId, 4303L);
   }

   public void createTrat(RentSheet sheet, PropertyInfo property, Long objId) {
      Date rtnDate;
      try {
         rtnDate = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2019");
      } catch (ParseException e) {
         logger.error("Trat date parse error");
         rtnDate = new Date();
      }
      
      I3Relation rtn = new I3Relation();
      rtn.setCatCategoryId(tratCat.getId());
      rtn.setRtnDate(rtnDate);
      rtn.setStsStatusId(2L);
      rtn.setRtnNumber("XLSX_2019_TRAT_" + sheet.cntrNum + "_" + property.propNum);
      rtnRepo.save(rtn);

      I3TratComponent trat = new I3TratComponent();
      trat.setCfvClassifierValueId(clfRepo.findByCfvCode("CLF_PROPERTIES_TYPE_OBJ_ECONOMIC").getId());
      trat.setRelation(rtn);
      tratRepo.save(trat);

      I3TratProperty tp = new I3TratProperty();
      tp.setTrat(trat);
      tp.setTrpBalanceCost(property.propCost);
      trapPropRepo.save(tp);
 
      I3ObjRtn obr = new I3ObjRtn();
      obr.setObjObjectId(objId);
      obr.setRtnRelationId(rtn.getId());
      obr.setObrType(2400L);
      obrRepo.save(obr);
  }
}