package ru.devag.kamc.nto;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.commons.lang3.StringUtils;

import ru.devag.kamc.SheetInfo;

public class NtoSchemeSheet extends SheetInfo<NtoSchemeItem> {
   
   public NtoSchemeSheet(Sheet sheet) {
      super(sheet, NtoSchemeItem.class, 1);
   }

   public boolean canAdd(NtoSchemeItem item) {
      return item.num != null || item.oldNum != null || 
         !StringUtils.isEmpty(item.placement) || 
         item.area != null || item.prclArea != null;
   }
}
