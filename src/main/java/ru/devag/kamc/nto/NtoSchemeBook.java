package ru.devag.kamc.nto;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.devag.kamc.BookInfo;

public class NtoSchemeBook extends BookInfo {
   private static Logger logger = LoggerFactory.getLogger(NtoBook.class);

   public NtoSchemeBook(XSSFWorkbook workbook) {
      super(workbook);
   }

   protected NtoSchemeSheet importSheet(Sheet sheet) {
      NtoSchemeSheet ntoSheet = new NtoSchemeSheet(sheet);
      return (ntoSheet.items == null || ntoSheet.items.size() == 0) ? 
         null : ntoSheet;
   }
}
