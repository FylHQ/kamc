package ru.devag.kamc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SheetInfo {
    private static Logger logger = LoggerFactory.getLogger(SheetInfo.class);

    public static final String SBJ = "Арендатор:";
    public static final String INN = "ИНН:";
    public static final String CNTR_NUM = "№ договора аренды:";
    public static final String CNTR_START_DATE = "Дата заключения договора аренды:";
    public static final String CNTR_END_DATE = "Дата расторжения договора аренды:";
    public static final String CNTR_MONTH_SUM = "Размер ежемесячной арендной платы по договору:";
    public static final String CNTR_YEAR_SUM = "Размер годовой арендной платы по договору:";

    public String sheetName;
    public String subject;
    public String inn;
    public String cntrNum;
    public Date cntrStartDate;
    public Date cntrEndDate;
    public String cntrMonthSum;
    public String cntrYearSum;
    public boolean isExists;

    public List<PropertyInfo> property = new ArrayList<>();

    void set(Cell cell) {
        String cellText = cell.toString();

        if (cellText.startsWith(SBJ)) {
            subject = cellText.substring(SBJ.length()).trim();
        } else if (cellText.startsWith(INN)) {
            inn = cellText.substring(INN.length()).trim();
        } else if (cellText.startsWith("№ договора аренды:")) {
            cntrNum = cellText.substring(CNTR_NUM.length()).trim();
            if (cntrNum.equals("б/н")) {
                cntrNum = "б/н (НФ)";
            }
        } else if (cellText.startsWith("Дата заключения договора аренды:")) {
            String strDate = cellText.substring(CNTR_START_DATE.length()).trim();
            if (!StringUtils.isEmpty(strDate)) {
                try {
                    cntrStartDate = new SimpleDateFormat("dd.MM.yyyy").parse(strDate);
                } catch (ParseException e) {
                    logger.error("Некорректный формат даты заключения: {}", strDate);
                }
            }
            
        } else if (cellText.startsWith("Дата расторжения договора аренды:")) {
            String strDate = cellText.substring(CNTR_END_DATE.length()).trim();
            if (!StringUtils.isEmpty(strDate) && !strDate.equals("неопределенный срок")) {
                try {
                    cntrEndDate = new SimpleDateFormat("dd.MM.yyyy").parse(strDate);
                } catch (ParseException e) {
                    logger.error("Некорректный формат даты расторжения: {}", strDate);
                }
            }
        } else if (cellText.startsWith("Размер ежемесячной арендной платы по договору:")) {
            cntrMonthSum = cellText.substring(CNTR_MONTH_SUM.length()).trim();
        } else if (cellText.startsWith("Размер годовой арендной платы по договору:")) {
            cntrYearSum = cellText.substring(CNTR_YEAR_SUM.length()).trim();
        }
   }
}