package ru.devag.kamc;

import java.util.ArrayList;
import java.util.List;

import static ru.devag.kamc.ImportUtils.*;

import java.beans.Statement;
import java.util.*;
import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SheetInfo<T> {
   private static Logger logger = LoggerFactory.getLogger(BookInfo.class);

   public Class<T> itemClass;
   public List<T> items;
   public int headerSize = 1;

   public boolean canAdd(T item) {
      return true;
   }

   public SheetInfo(Class<T> itemClass) {
      this.itemClass = itemClass;
   }

   public SheetInfo(Sheet sheet, Class<T> itemClass, int headerSize) {
      this.itemClass = itemClass;
      this.headerSize = headerSize;
      importItems(sheet);
   }

   public Map<CellIndex, Field> getCellIndexes() {
      Map<CellIndex, Field> cellIndexes = new HashMap<>();
      for (Field field : itemClass.getDeclaredFields()) {
         if (field.isAnnotationPresent(CellIndex.class)) {
            CellIndex ci = field.getAnnotation(CellIndex.class);
            cellIndexes.put(ci, field);
         }
      }

      return cellIndexes;
   }

   public void importItems(Sheet sheet) {
      items = new ArrayList<>();

      Map<CellIndex, Field> cellIndexes = getCellIndexes();

      Map<Integer, CellIndex> indexes = new LinkedHashMap<>();
      Iterator<Row> rowIt = sheet.iterator();
      while (rowIt.hasNext()) {
         Row row = rowIt.next();

         T item;
         try {
            item = this.itemClass.newInstance();
         } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Cannot instantiate an item: {}", e);
            return;
         }

         Iterator<Cell> cellIt = row.cellIterator();
         while (cellIt.hasNext()) {
            Cell cell = cellIt.next();
            if (row.getRowNum() < headerSize) {
               cellIndexes.forEach((ci, field) -> {
                  if (cell.toString().contains(ci.value())) {
                     indexes.put(cell.getColumnIndex() + ci.offset(), ci);
                  }
               });
            } else {
               CellIndex ci = indexes.get(cell.getColumnIndex());
               if (ci != null) {
                  Object obj;
                  Field field = cellIndexes.get(ci);
                  if (field.getType() == String.class) {
                     obj = getString(cell);
                  } else if (field.getType() == Date.class) {
                     obj = getDate(cell);
                  } else if (field.getType() == Double.class) {
                     obj = getNumeric(cell);
                  } else if (field.getType() == Integer.class) {
                     Double v = getNumeric(cell);
                     obj = v == null ? null : v.intValue();
                  } else {
                     obj = null;
                     logger.error("Неподдерживаемый тип: {}", field.getType().getName());
                  }
                  Statement stmt = new Statement(item, "set" + StringUtils.capitalize(field.getName()), 
                     new Object[] { obj });
                  try {
                     stmt.execute();
                  } catch (Exception e) {
                     logger.error("Set error: {}", e);
                  }
               }
            }
         }

         if (canAdd(item)) {
            items.add(item);
         }
      }
   }
}
   
