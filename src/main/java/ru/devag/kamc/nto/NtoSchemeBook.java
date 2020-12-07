package ru.devag.kamc.nto;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.devag.kamc.BookInfo;

public class NtoSchemeBook extends BookInfo {
   public NtoSchemeBook(XSSFWorkbook workbook) {
      super(workbook);
   }

   protected NtoSchemeSheet importSheet(Sheet sheet) {
      NtoSchemeSheet ntoSheet = new NtoSchemeSheet(sheet);
      return (ntoSheet.items == null || ntoSheet.items.size() == 0) ? 
         null : ntoSheet;
   }
}
