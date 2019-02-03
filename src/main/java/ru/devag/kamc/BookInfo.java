package ru.devag.kamc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookInfo {
   private static Logger logger = LoggerFactory.getLogger(MainController.class);

   public List<SheetInfo> sheets = new ArrayList<>();

   public BookInfo(XSSFWorkbook workbook) {
      Iterator<Sheet> sheetIt = workbook.iterator();
      
      while(sheetIt.hasNext()) {
          Sheet sheet = sheetIt.next();
          importSheet(sheet);
          //if (sheets.size() == 5) break;
      }
   }

   void importSheet(Sheet sheet) {

      SheetInfo sheetInfo = new SheetInfo();
      sheetInfo.sheetName = sheet.getSheetName();
      Iterator<Row> rowIt = sheet.iterator();
      boolean isTable = false;

      
      //шапка листа до перечня
      while (rowIt.hasNext() && !isTable) {
         Row row = rowIt.next();

         Iterator<Cell> cellIterator = row.cellIterator();
         while (cellIterator.hasNext() && !isTable) {
            Cell cell = cellIterator.next();

            if (cell.getColumnIndex() == 0) {
               sheetInfo.set(cell);
            }

            if (cell.toString().equalsIgnoreCase("Перечень имущества по договору аренды")) {
               isTable = true;
            }
         }
      }

      if (!isTable) {
          throw new RuntimeException("Не удалось найти перечень: " + sheet.getSheetName());
      }

      //индексы таблицы
      PropIndexes indexes = new PropIndexes();   
      if (rowIt.hasNext()) {
          Row row = rowIt.next();
          Iterator<Cell> cellIterator = row.cellIterator();
          while (cellIterator.hasNext()) {
              Cell cell = cellIterator.next();
              indexes.set(cell);
          }
      }

      //пропускаем одну строку с номерами заголовков
      if (rowIt.hasNext()) {
          rowIt.next();
      }

      //данные
      while (rowIt.hasNext()) {
          Row row = rowIt.next();
          PropertyInfo propertyInfo = new PropertyInfo();
          Iterator<Cell> cellIterator = row.cellIterator();
          while (cellIterator.hasNext()) {
              Cell cell = cellIterator.next();
              propertyInfo.set(cell, indexes);
          }
          if (propertyInfo.propNum != null && !propertyInfo.propNum.equals("")) {
              sheetInfo.property.add(propertyInfo);
          }
      }

      sheets.add(sheetInfo);
  }
}