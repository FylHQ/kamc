package ru.devag.kamc.smbusiness;

import java.util.Date;
import ru.devag.kamc.CellIndex;

public class SmbItem {
   @CellIndex("№ п/п")
   Integer pos;

   @CellIndex("Наименование / ФИО")
   String subject;
   
   @CellIndex("Категория")
   String category;
   
   @CellIndex("ОГРН")
   String ogrn;
   
   @CellIndex("ИНН")
   String inn;
   
   @CellIndex("Дата включения в реестр")
   Date regDate;

   public Integer getPos() {
      return pos;
   }

   public void setPos(Integer pos) {
      this.pos = pos;
   }
   
   

   public String getCategory() {
      return category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public String getOgrn() {
      return ogrn;
   }

   public void setOgrn(String ogrn) {
      this.ogrn = ogrn;
   }

   public String getInn() {
      return inn;
   }

   public void setInn(String inn) {
      this.inn = inn;
   }

   public Date getRegDate() {
      return regDate;
   }

   public void setRegDate(Date regDate) {
      this.regDate = regDate;
   }

   public String getSubject() {
      return subject;
   }

   public void setSubject(String subject) {
      this.subject = subject;
   }
   
}

