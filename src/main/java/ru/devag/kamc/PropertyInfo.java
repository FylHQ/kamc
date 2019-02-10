package ru.devag.kamc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyInfo {
   private static Logger logger = LoggerFactory.getLogger(PropertyInfo.class);

   public enum PropType {
      APRM,
      NETW,
      TRAN,
      KFXA
   }

   public String propNum;
   public String propName = null;
   public String propAddress = null;
   public String propArea = null;
   public String propLength = null;
   public String propCadnum = null;
   public Double propCost = null;
   public String propMonthSum;
   public String propYearSum;
   public String propYear = null;
   public boolean isPart = false;

   public PropType propType;

   void set(Cell cell, PropIndexes indexes) {
      int index = cell.getColumnIndex();
      
      if (index == indexes.numIndex) {
         propNum = cell.toString();
      } else if (index == indexes.nameIndex) {
         propName = cell.toString();
         String prefix = "часть крыши здания ";
         if (propName.toLowerCase().startsWith(prefix)) {
            isPart = true;
            propName = "Здание " + propName.substring(prefix.length());
            logger.info("Часть: {}", propName);
         }
         
      } else if (index == indexes.addressIndex) {
         String ceilStr = cell.toString();
         if (ceilStr.startsWith("г.") && !ceilStr.equalsIgnoreCase("г. Петропавловск-Камчатский")) {
            Pattern p = Pattern.compile("г\\.\\s*Петропавловск\\s*-\\s*(Камчатский|Камччатский),{0,1}\\s*(ул\\.|ш\\.|пр\\.|проезд|пер\\.|шоссе|проспект|б-р|площадь|улица)\\s+(.*)", Pattern.CASE_INSENSITIVE|Pattern.UNICODE_CASE);
            Matcher m = p.matcher(ceilStr);
            if (m.find()) {
               ceilStr = m.group(3);
            } else {
               p = Pattern.compile("г\\.\\s*Петропавловск\\s*-\\s*(Камчатский|Камччатский),{0,1}\\s*(.*)", Pattern.CASE_INSENSITIVE|Pattern.UNICODE_CASE);
               m = p.matcher(ceilStr);
               if (m.find()) {
                  ceilStr = m.group(2);
               }
            }
            propAddress = ceilStr.replace("д. ", " ").replace("д.", " ")
               .replaceAll("\\s+", " ") //много пробелов
               .replaceAll("\\s+д\\s+(\\d+)", " $1") //д без точки
               .replaceAll("\\s+№(\\d+)", " $1") //символ номера
               .replaceAll("([а-яА-ЯеЁ])\\s+(\\d)", "$1, $2") //нет запятой перед числом
               .replaceAll("(\\d)\\s+([а-яА-ЯеЁ])$", "$1$2") //пробел между номером и литерой в конце
               ; 
            //logger.info("{}", propAddress);
         }

      } else if (index == indexes.areaIndex) {
         propArea = cell.toString();
      } else if (index == indexes.lengthIndex) {
         propLength = cell.toString();
      } else if (index == indexes.cadnumIndex) {
         propCadnum = cell.toString();
      } else if (index == indexes.costIndex) {
         if (cell.getCellType() == CellType.NUMERIC) {
            propCost = cell.getNumericCellValue();
         } else {
            String strVal = cell.getStringCellValue().replace(',', '.').replaceAll("[^\\d.]", "");
            if (StringUtils.countMatches(strVal, ".") == 2) {
               logger.warn("Найдено два разделителя. Убираем первый: [{}]: {}", propName, strVal);
               strVal = strVal.replaceFirst("\\.", "");
            }
            if (!strVal.equals("")) {
               try {
                  propCost = Double.parseDouble(strVal);
               } catch (NumberFormatException e) {
                  logger.error("Ошибка конвертации: {}", strVal);
                  propCost = null;   
               }
            } else {
               propCost = null;
            }
         }
      } else if (index == indexes.monthSumIndex) {
         propMonthSum = cell.toString();
      } else if (index == indexes.yearSumIndex) {
         propYearSum = cell.toString();
      } else if (index == indexes.yearIndex) {
         propYear = cell.toString();
      }
   }

}

