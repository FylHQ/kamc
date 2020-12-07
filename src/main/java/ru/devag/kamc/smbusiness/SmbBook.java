package ru.devag.kamc.smbusiness;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.devag.kamc.BookInfo;

public class SmbBook extends BookInfo {
   public SmbBook(XSSFWorkbook workbook) {
      super(workbook);
   }

   protected SmbSheet importSheet(Sheet sheet) {
      SmbSheet smbSheet = new SmbSheet(sheet);
      smbSheet.setSheetName(sheet.getSheetName());
      smbSheet.setDescription("Число строк: " + smbSheet.items.size());
      return smbSheet;
   }
}
