package ru.devag.kamc.prclcost;

import org.apache.poi.ss.usermodel.Sheet;

import ru.devag.kamc.SheetInfo;

public class PrclCostSheet extends SheetInfo<PrclCostItem> {
   private String sheetName;
   private String description;

   public PrclCostSheet(Sheet sheet) {
      super(sheet, PrclCostItem.class, 1);
   }

   @Override
   public boolean onAdd(PrclCostItem item, int rowNum) {
      return item.cadnum != null;
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
