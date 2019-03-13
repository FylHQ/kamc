package ru.devag.kamc.nto;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

import ru.devag.kamc.SheetInfo;

public class NtoSheet extends SheetInfo<NtoItem> {

   public NtoSheet(Sheet sheet) {
      super(sheet, NtoItem.class, 2);
   }

   @Override
   public boolean canAdd(NtoItem item) {
      return item.cntrNum != null && !StringUtils.isEmpty(item.status);
   }
}
