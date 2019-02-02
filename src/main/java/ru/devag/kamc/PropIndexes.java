package ru.devag.kamc;

import org.apache.poi.ss.usermodel.Cell;

public class PropIndexes {
   
   public static final String PROP_NUM = "№ п/п";
   public static final String PROP_NAME = "наименование";
   public static final String PROP_ADDRESS = "адрес";
   public static final String PROP_AREA = "площадь";
   public static final String PROP_LENGTH = "протяженность";
   public static final String PROP_CADNUM = "кадастровый номер";
   public static final String PROP_COST = "балансовая стоимость";
   public static final String PROP_MONTH_SUM = "ежемесячная сумма арендной платы";
   public static final String PROP_YEAR_SUM = "годовая сумма арендной платы";

   int numIndex = -1;
   int nameIndex = -1;
   int addressIndex = -1;
   int areaIndex = -1;
   int lengthIndex = -1;
   int cadnumIndex = -1;
   int costIndex = -1;
   int monthSumIndex = -1;
   int yearSumIndex = -1;

   int set(Cell cell) {
      int index = cell.getColumnIndex();
      String cellText = cell.toString().toLowerCase();

      if (cellText.equalsIgnoreCase(PROP_NUM)) {
         numIndex = index;
      } else if (cellText.startsWith(PROP_NAME)) {
         nameIndex = index;
      } else if (cellText.startsWith(PROP_ADDRESS)) {
         addressIndex = index;
      } else if (cellText.startsWith(PROP_AREA)) {
         areaIndex = index;
      } else if (cellText.startsWith(PROP_LENGTH)) {
         lengthIndex = index;
      } else if (cellText.startsWith(PROP_CADNUM)) {
         cadnumIndex = index;
      } else if (cellText.startsWith(PROP_COST)) {
         costIndex = index;
      } else if (cellText.startsWith(PROP_MONTH_SUM)) {
         monthSumIndex = index;
      } else if (cellText.startsWith(PROP_YEAR_SUM)) {
         yearSumIndex = index;
      } else {
         index = -1;
      }


      return index;
   }
}