package ru.devag.kamc;

import java.util.*;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public abstract class BookInfo<T extends SheetInfo> {

   public List<SheetInfo> sheets = new ArrayList<>();

   public BookInfo(XSSFWorkbook workbook) {
      sheets.clear();

      Iterator<Sheet> sheetIt = workbook.iterator();
      
      while(sheetIt.hasNext()) {
         Sheet sheet = sheetIt.next();
         sheets.add(importSheet(sheet));
      }
   }

   protected abstract T importSheet(Sheet sheet);
}