package ru.devag.kamc.nto;

import org.apache.poi.ss.usermodel.Sheet;
import ru.devag.kamc.SheetInfo;

public class NtoSchemeSheet extends SheetInfo<NtoSchemeItem> {
   
   public NtoSchemeSheet(Sheet sheet) {
      super(sheet, NtoSchemeItem.class, 1);
   }

   @Override
   public boolean onAdd(NtoSchemeItem item, int rowNum) {
      return item.num != null;
   }
}
