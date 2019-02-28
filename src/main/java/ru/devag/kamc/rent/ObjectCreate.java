package ru.devag.kamc.rent;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import ru.devag.kamc.model.*;
import ru.devag.kamc.repo.*;

@Service
public class ObjectCreate {
   @Autowired
   I3ObjectRepository objRepo;

   @Autowired
   I3KfxaComponentRepository kfxaRepo;

   @Autowired
   I3CategoryRepository catRepo;

   public I3Object createKfxa(RentSheet sheet, PropertyInfo property) {
      I3Object obj = new I3Object();

      StringBuilder sb = new StringBuilder();
      sb.append(property.propName);

      if (property.propCount != null) {
         sb.append(" ").append(property.propCount).append(" шт.");
      }

      if (property.propPlacement != null) {
         sb.append(" (").append(property.propPlacement).append(")");
      }

      obj.setObjDescription(sb.toString());
      obj.setCatCategoryId(catRepo.findByCatCode("KFXA").getId());

      obj.setStsStatusId(2L);
      obj.setObjNumber("XLSX_2019_" + sheet.cntrNum + "_" + property.propNum);

      objRepo.save(obj);

      I3KfxaComponent kfxa = new I3KfxaComponent();
      kfxa.setKfaIsHighCost(property.propCost != null && property.propCost >= 50000 ? "T" : "F");
      kfxa.setObject(obj);

      kfxaRepo.save(kfxa);

      return obj;
   }
}