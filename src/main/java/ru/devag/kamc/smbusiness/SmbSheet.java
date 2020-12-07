package ru.devag.kamc.smbusiness;

import org.apache.poi.ss.usermodel.Sheet;

import ru.devag.kamc.SheetInfo;

public class SmbSheet extends SheetInfo<SmbItem> {
   private String sheetName;
   private String description;

   public SmbSheet(Sheet sheet) {
      super(sheet, SmbItem.class, 3);
   }

   @Override
   public boolean onAdd(SmbItem item, int rowNum) {
      return item.pos != null;
   }

   public String getSheetName() {
      return sheetName;
   }

   public void setSheetName(String sheetName) {
      this.sheetName = sheetName;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }
}
