package ru.devag.kamc;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyInfo {
   private static Logger logger = LoggerFactory.getLogger(PropertyInfo.class);

   public String propNum;
   public String propName;
   public String propAddress;
   public String propArea;
   public String propLength;
   public String propCadnum;
   public Double propCost;
   public String propMonthSum;
   public String propYearSum;

   void set(Cell cell, PropIndexes indexes) {
      int index = cell.getColumnIndex();
      
      if (index == indexes.numIndex) {
         propNum = cell.toString();
      } else if (index == indexes.nameIndex) {
         propName = cell.toString();
      } else if (index == indexes.addressIndex) {
         propAddress = cell.toString();
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
      }
   }

}

