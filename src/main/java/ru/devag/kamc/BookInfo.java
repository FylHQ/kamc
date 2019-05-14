package ru.devag.kamc;

import java.util.*;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class BookInfo {
   private static Logger logger = LoggerFactory.getLogger(BookInfo.class);

   public List<SheetInfo<?>> sheets = new ArrayList<>();

   public BookInfo(XSSFWorkbook workbook) {
      sheets.clear();

      Iterator<Sheet> sheetIt = workbook.iterator();
      
      while(sheetIt.hasNext()) {
         Sheet sheet = sheetIt.next();
         SheetInfo<?> sheetInfo = importSheet(sheet);
         if (sheetInfo != null)
            sheets.add(sheetInfo);
      }
   }

   public List<SheetInfo<?>> getSheets() {
      return sheets;
   }

   protected abstract SheetInfo<?> importSheet(Sheet sheet);
}