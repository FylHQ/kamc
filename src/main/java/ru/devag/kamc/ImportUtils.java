package ru.devag.kamc;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImportUtils {
   private static Logger logger = LoggerFactory.getLogger(ImportUtils.class);
   private static Pattern DATE = Pattern.compile("\\d{2}\\s*\\.\\s*\\d{2}\\s*\\.\\s*\\d{4}");

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
      switch (cell.getCellType()) {
         case NUMERIC:
            return cell.getNumericCellValue();
         case STRING:
            String strVal = cell.getStringCellValue().replace(',', '.').replaceAll("[^\\d.]", "");
            return getNumeric(strVal);
         case FORMULA:
            switch(cell.getCachedFormulaResultType()) {
               case NUMERIC:
                  return cell.getNumericCellValue();
               case STRING:
                  String strFormula = cell.getRichStringCellValue().getString().replace(',', '.').replaceAll("[^\\d.]", "");
                  logger.error("Cell formula: {}/{}", cell.getRow().getRowNum(), cell.getColumnIndex());
                  return getNumeric(strFormula);
               default:
                  logger.debug("Unsupported cell formula type: {}", cell.getCellType());
                  return null;      
            }
         case BLANK:
            return null;
         default:
            logger.error("Unsupported cell type: {}", cell.getCellType());
            return null;
      
      }
   }

   public static Date getDate(Cell cell) {
      try {
         return cell.getDateCellValue();
      } catch (Exception e1) {
         String strVal = StringUtils.strip(cell.toString().replaceAll("\\.+", "."), " .");
         try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(strVal);
         } catch (ParseException e2) {
            try {
               return new SimpleDateFormat("dd.MMM.yyyy", new Locale("ru")).parse(strVal);
            } catch (ParseException e3) {
               Matcher m = DATE.matcher(strVal);
               
               if (m.find()) {
                  strVal = m.group().replaceAll("\\s", "");
                  try {
                     logger.info("Дата получена из значения: {}", cell.toString());
                     return new SimpleDateFormat("dd.MM.yyyy").parse(strVal);
                  } catch (ParseException e4) {
                     logger.error("Ошибка определения даты: {}", cell.toString());
                     return null;
                  }
               } else {
                  logger.error("Некорректный формат даты: {}", cell.toString());
                  return null;
               }
            }
            
         }

      }
   }
}