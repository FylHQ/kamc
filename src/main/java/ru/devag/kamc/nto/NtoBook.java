package ru.devag.kamc.nto;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.devag.kamc.BookInfo;

public class NtoBook extends BookInfo {
   public NtoBook(XSSFWorkbook workbook) {
      super(workbook);
   }

   protected NtoSheet importSheet(Sheet sheet) {
      NtoSheet ntoSheet = new NtoSheet(sheet);
      return (ntoSheet.items == null || ntoSheet.items.size() == 0) ? null : ntoSheet;
   }
}
