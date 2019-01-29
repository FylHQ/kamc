package ru.devag.kamc;

import org.apache.poi.ss.usermodel.Cell;

public class PropertyInfo {
   public String propNum;
   public String propName;
   public String propAddress;
   public String propArea;
   public String propLength;
   public String propCadnum;
   public String propCost;
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
         propCost = cell.toString();
      } else if (index == indexes.monthSumIndex) {
         propMonthSum = cell.toString();
      } else if (index == indexes.yearSumIndex) {
         propYearSum = cell.toString();
      }
   }

}

