package ru.devag.kamc;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImportUtils {
   private static Logger logger = LoggerFactory.getLogger(PropertyInfo.class);

   public static Double getNumeric(String strVal) {
      if (StringUtils.countMatches(strVal, ".") == 2) {
         logger.warn("Найдено два разделителя. Убираем первый: {}", strVal);
         strVal = strVal.replaceFirst("\\.", "");
      }
      if (!strVal.equals("")) {
         try {
            return Double.parseDouble(strVal);
         } catch (NumberFormatException e) {
            logger.error("Ошибка конвертации: {}", strVal);
            return null;   
         }
      }
      return null;
   }
   
   public static Double getNumeric(Cell cell) {
      if (cell.getCellType() == CellType.NUMERIC) {
         return cell.getNumericCellValue();
      } else {
         String strVal = cell.getStringCellValue().replace(',', '.').replaceAll("[^\\d.]", "");
         return getNumeric(strVal);
      }
   }
}