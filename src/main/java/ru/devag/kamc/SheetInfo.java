package ru.devag.kamc;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;

public class SheetInfo {
   public static final String SBJ = "Арендатор:";
   public static final String INN = "ИНН:";
   public static final String CNTR_NUM = "№ договора аренды:";
   public static final String CNTR_START_DATE = "Дата заключения договора аренды:";
   public static final String CNTR_END_DATE = "Дата расторжения договора аренды:";
   public static final String CNTR_MONTH_SUM = "Размер ежемесячной арендной платы по договору:";
   public static final String CNTR_YEAR_SUM = "Размер годовой арендной платы по договору:";

   public String subject;
   public String inn;
   public String cntrNum;
   public String cntrStartDate;
   public String cntrEndDate;
   public String cntrMonthSum;
   public String cntrYearSum;

   public List<PropertyInfo> property = new ArrayList<>();

   void set(Cell cell) {
      String cellText = cell.toString();

        if (cellText.startsWith(SBJ)) {
            subject = cellText.substring(SBJ.length()).trim();
        } else if (cellText.startsWith(INN)) {
            inn = cellText.substring(INN.length()).trim();
        } else if (cellText.startsWith("№ договора аренды:")) {
            cntrNum = cellText.substring(CNTR_NUM.length()).trim();
        } else if (cellText.startsWith("Дата заключения договора аренды:")) {
            cntrStartDate = cellText.substring(CNTR_START_DATE.length()).trim();
        } else if (cellText.startsWith("Дата расторжения договора аренды:")) {
            cntrEndDate = cellText.substring(CNTR_END_DATE.length()).trim();
        } else if (cellText.startsWith("Размер ежемесячной арендной платы по договору:")) {
            cntrMonthSum = cellText.substring(CNTR_MONTH_SUM.length()).trim();
        } else if (cellText.startsWith("Размер годовой арендной платы по договору:")) {
            cntrYearSum = cellText.substring(CNTR_YEAR_SUM.length()).trim();
        }
   }

}