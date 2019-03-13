package ru.devag.kamc.nto;

import ru.devag.kamc.CellIndex;

public class NtoSchemeItem {
   @CellIndex("№ п/п")
   Integer num;

   @CellIndex("Старая строка")
   Integer oldNum;

   @CellIndex("Место размещения")
   String placement;
   
   @CellIndex("Площадь земельного участка")
   Double prclArea;
   
   @CellIndex("Площадь  объекта")
   Double area;

   @CellIndex("Специализация")
   String specType;
   
   @CellIndex("Тип торгового объекта")
   String objType;
   
   @CellIndex("Период размещения")
   String period;
   
   @CellIndex("Фамилия")
   String surname;

   /**
    * @return the num
    */
   public Integer getNum() {
      return num;
   }

   /**
    * @param num the num to set
    */
   public void setNum(Integer num) {
      this.num = num;
   }

   /**
    * @return the oldNum
    */
   public Integer getOldNum() {
      return oldNum;
   }

   /**
    * @param oldNum the oldNum to set
    */
   public void setOldNum(Integer oldNum) {
      this.oldNum = oldNum;
   }

   /**
    * @return the placement
    */
   public String getPlacement() {
      return placement;
   }

   /**
    * @param placement the placement to set
    */
   public void setPlacement(String placement) {
      this.placement = placement;
   }

   /**
    * @return the prclArea
    */
   public Double getPrclArea() {
      return prclArea;
   }

   /**
    * @param prclArea the prclArea to set
    */
   public void setPrclArea(Double prclArea) {
      this.prclArea = prclArea;
   }

   /**
    * @return the area
    */
   public Double getArea() {
      return area;
   }

   /**
    * @param area the area to set
    */
   public void setArea(Double area) {
      this.area = area;
   }

   /**
    * @return the specType
    */
   public String getSpecType() {
      return specType;
   }

   /**
    * @param specType the specType to set
    */
   public void setSpecType(String specType) {
      this.specType = specType;
   }

   /**
    * @return the objType
    */
   public String getObjType() {
      return objType;
   }

   /**
    * @param objType the objType to set
    */
   public void setObjType(String objType) {
      this.objType = objType;
   }

   /**
    * @return the period
    */
   public String getPeriod() {
      return period;
   }

   /**
    * @param period the period to set
    */
   public void setPeriod(String period) {
      this.period = period;
   }

   /**
    * @return the surname
    */
   public String getSurname() {
      return surname;
   }

   /**
    * @param surname the surname to set
    */
   public void setSurname(String surname) {
      this.surname = surname;
   }
      
   
}

