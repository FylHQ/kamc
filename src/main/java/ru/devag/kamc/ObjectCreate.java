package ru.devag.kamc;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import ru.devag.kamc.model.*;
import ru.devag.kamc.nto.*;
import ru.devag.kamc.rent.*;
import ru.devag.kamc.repo.*;

@Service
public class ObjectCreate {
   private static Logger logger = LoggerFactory.getLogger(ObjectCreate.class);

   @Autowired
   I3ObjectRepository objRepo;

   @Autowired
   I3LandComponentRepository landRepo;

   @Autowired
   I3KfxaComponentRepository kfxaRepo;

   @Autowired
   I3NetwComponentRepository netwRepo;

   @Autowired
   I3CategoryRepository catRepo;

   @Autowired
   I3ClassifierValueRepository clfRepo;

   @Autowired
   I3ObjAddRepository obaRepo;

   @Autowired
   I3ExtAttributeValueRepository extvRepo;

   @Autowired
   I3NobjComponentRepository nobjRepo;

   public I3Object createObj(RentSheet sheet, PropertyInfo property, String catCode, boolean addAddress) {
      I3Object obj = new I3Object();

      StringBuilder sb = new StringBuilder();
      sb.append(property.propName);

      if (property.propCount != null) {
         sb.append(" ").append(property.propCount).append(" шт.");
      }

      if (property.propAddressShort != null && addAddress) {
         sb.append(" (").append(property.propAddressShort).append(")");
      }

      obj.setObjDescription(sb.toString());
      obj.setCatCategoryId(catRepo.findByCatCode(catCode).getId());

      obj.setStsStatusId(2L);
      obj.setObjNumber("XLSX_2019_" + sheet.cntrNum + "_" + property.propNum);

      objRepo.save(obj);

      return obj;
   }
   
   public I3Object createKfxa(RentSheet sheet, PropertyInfo property) {
      I3Object obj = createObj(sheet, property, "KFXA", true);

      I3KfxaComponent kfxa = new I3KfxaComponent();
      kfxa.setKfaIsHighCost(property.propCost != null && property.propCost >= 50000 ? "T" : "F");
      kfxa.setObject(obj);

      kfxaRepo.save(kfxa);

      return obj;
   }

   public I3Object createNetw(RentSheet sheet, PropertyInfo property) {
      I3Object obj = createObj(sheet, property, "NETW", false);

      I3LandComponent land = new I3LandComponent();
      land.setObject(obj);
      landRepo.save(land);

      I3NetwComponent netw = new I3NetwComponent();
      netw.setLand(land);

      netw.setCfvClassifierValueId(clfRepo.findByCfvCode("IM_KOM_TYPE8").getId());
      netw.setNetIsfinish("F");
      netw.setNetIsMovable("T");

      if (property.propLength != null) {
         netw.setNetLength(property.propLength);
      }
      if (property.propCadnum != null) {
         netw.setNetCadastralInfo(property.propCadnum);
      }
      if (property.propInfo != null) {
         netw.setNetDescription(property.propInfo);
      }

      I3ObjAdd oba = new I3ObjAdd();
      oba.setObjObjectId(obj.getId());
      oba.setAddAddressId(34613641L);
      obaRepo.save(oba);

      if (property.propAddressShort != null) {
         I3ExtAttributeValue extv = new I3ExtAttributeValue();
         extv.setExaExtAttributeId(30804612L);
         extv.setExvComId(obj.getId());
         extv.setExvText(property.propAddressShort);
         extvRepo.save(extv);
      }

      netwRepo.save(netw);

      return obj;
   }

   public I3ClassifierValue getOrCreateClf(String val, String parentCode) {
      I3ClassifierValue clfParent = clfRepo.findByCfvCode(parentCode);
      List<I3ClassifierValue> clfs = clfRepo.findByCfvValueAndCfvParentId(val, clfParent.getId());
      if (clfs.size() > 0) {
         return clfs.get(0);
      }
      
      I3ClassifierValue clf = new I3ClassifierValue();
      clf.setCfvValue(val);
      clf.setCfvCode(parentCode + "_AUTO_" + RandomStringUtils.random(4, false, true));
      clf.setCfvParentId(clfParent.getId());
      clfRepo.save(clf);

      logger.warn("A new classifier has been created: {} ({})", val, parentCode);

      return clf;
   }

   public I3NobjComponent getOrCreateNobj(NtoSchemeItem item, NtoItem ntoItem) {
      Optional<I3NobjComponent> maybeNobj = nobjRepo.findByNobActualNumber(String.valueOf(item.getNum()));
      if (maybeNobj.isPresent()) {
         return maybeNobj.get();
      }

      I3Object obj = new I3Object();
      obj.setCatCategoryId(catRepo.findByCatCode("NOBJ").getId());

      obj.setStsStatusId(1L);
      obj.setObjNumber("XLSX_2019_NOBJ_" + item.getNum());

      objRepo.save(obj);

      I3NobjComponent nobj = new I3NobjComponent();
      nobj.setObject(obj);
      nobj.setNobActualNumber(String.valueOf(item.getNum()));
      nobj.setNobPlacement(item.getPlacement());
      nobj.setNobArea(item.getArea());
      nobj.setNobPrclArea(item.getPrclArea());
      
      //из договора
      nobj.setNobAvgUnitCost(ntoItem.getCadCostAVG());
      nobj.setNobCadBlock(ntoItem.getCadBlock().longValue());

      I3ClassifierValue clfType = getOrCreateClf(item.getObjType().replace("«", "\"").replace("»", "\""), "CLF_NTO_TYPE");
      I3ClassifierValue clfSpec = getOrCreateClf(item.getSpecType(), "CLF_NTO_SPEC_TYPE");
      
      nobj.setNobCfvTypeId(clfType.getId());
      nobj.setNobCfvSpecId(clfSpec.getId());

      nobjRepo.save(nobj);

      return nobj;
   }

}