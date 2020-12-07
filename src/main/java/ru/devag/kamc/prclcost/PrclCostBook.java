package ru.devag.kamc.prclcost;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.devag.kamc.BookInfo;

public class PrclCostBook extends BookInfo {
   public PrclCostBook(XSSFWorkbook workbook) {
      super(workbook);
   }

   protected PrclCostSheet importSheet(Sheet sheet) {
      PrclCostSheet prclCostSheet = new PrclCostSheet(sheet);
      prclCostSheet.setSheetName(sheet.getSheetName());
      prclCostSheet.setDescription("Число строк: " + prclCostSheet.items.size());
      return prclCostSheet;
   }
}
