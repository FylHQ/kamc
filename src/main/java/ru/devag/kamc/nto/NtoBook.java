package ru.devag.kamc.nto;

import static ru.devag.kamc.ImportUtils.*;

import java.beans.Statement;
import java.lang.reflect.Field;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.devag.kamc.BookInfo;

public class NtoBook extends BookInfo<NtoSheet> {
   private static Logger logger = LoggerFactory.getLogger(NtoBook.class);

   public NtoBook(XSSFWorkbook workbook) {
      super(workbook);
   }

   protected NtoSheet importSheet(Sheet sheet) {
      NtoSheet ntoSheet = new NtoSheet();

      Map<CellIndex, Field> cellIndexes = new HashMap<>();
      for (Field field : NtoItem.class.getDeclaredFields()) {
         if (field.isAnnotationPresent(CellIndex.class)) {
            CellIndex ci = field.getAnnotation(CellIndex.class);
            cellIndexes.put(ci, field);
         }
      }

      Map<Integer, CellIndex> indexes = new LinkedHashMap<>();

      Iterator<Row> rowIt = sheet.iterator();

      while (rowIt.hasNext()) {
         Row row = rowIt.next();
         // logger.info("rn: {}", row.getRowNum());

         NtoItem item = new NtoItem();
         Iterator<Cell> cellIt = row.cellIterator();
         while (cellIt.hasNext()) {
            Cell cell = cellIt.next();
            if (row.getRowNum() < 2) {
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
                     obj = cell.toString();
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

         if (item.cntrNum != null && !StringUtils.isEmpty(item.status)) {
            ntoSheet.items.add(item);
         }
      }
      /*indexes.forEach((k, v) -> {
         logger.info("{}: {}", v, k.value());
      });*/
      return ntoSheet;
   }

   
}
