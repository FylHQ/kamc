package ru.devag.kamc;

import java.util.*;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class BookInfo {
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