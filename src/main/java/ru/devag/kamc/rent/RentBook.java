package ru.devag.kamc.rent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.devag.kamc.BookInfo;
import ru.devag.kamc.rent.PropertyInfo.PropType;

public class RentBook extends BookInfo<RentSheet> {
    public RentBook(XSSFWorkbook workbook) {
        super(workbook);
    }
    
    protected RentSheet importSheet(Sheet sheet) {
      RentSheet rentSheet = new RentSheet();
      rentSheet.sheetName = sheet.getSheetName();
      Iterator<Row> rowIt = sheet.iterator();
      boolean isTable = false;

      
      //шапка листа до перечня
      while (rowIt.hasNext() && !isTable) {
         Row row = rowIt.next();

         Iterator<Cell> cellIterator = row.cellIterator();
         while (cellIterator.hasNext() && !isTable) {
            Cell cell = cellIterator.next();

            if (cell.getColumnIndex() == 0) {
               rentSheet.set(cell);
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
          if (propertyInfo.propNum != null) {
              if (propertyInfo.propArea != null) {
                propertyInfo.propType = PropType.APRM;
              } else if (propertyInfo.propLength != null ||
                    ((propertyInfo.propName.indexOf(" сете") != -1 || 
                        propertyInfo.propName.indexOf(" сеть") != -1 ||
                        propertyInfo.propName.indexOf(" сети") != -1 ||
                        propertyInfo.propName.indexOf("Кабельная линия") != -1
                    ) && 
                    !propertyInfo.propName.contains("Адаптер сети") &&
                    !propertyInfo.propName.contains("Насос сетевой") &&
                    !propertyInfo.propName.contains("Оборудование частотного регул") &&
                    !propertyInfo.propName.contains("Пластинчатый теплоообменник"))) {
                propertyInfo.propType = PropType.NETW;
              } else if (!StringUtils.isEmpty(propertyInfo.propYear)) {
                propertyInfo.propType = PropType.TRAN;
              } else {
                propertyInfo.propType = PropType.KFXA;
              }
              rentSheet.property.add(propertyInfo);
          }
      }

      return rentSheet;
  }
}