package ru.devag.kamc.nto;

import ru.devag.kamc.CellIndex;

public class NtoSchemeItem {
   @CellIndex("№ п/п")
   Integer num;

   @CellIndex("Место размещения")
   String placement;
   
   @CellIndex("Площадь земельного участка")
   Double prclArea;
   
   @CellIndex("Площадьобъекта")
   Double area;

   @CellIndex("Специализация")
   String specType;
   
   @CellIndex("Тип торгового объекта")
   String objType;
   
   @CellIndex("Период размещения")
   String period;
   
   @CellIndex("Примечание")
   String notes;

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
    * @return the notes
    */
   public String getNotes() {
      return notes;
   }

   /**
    * @param notes the notes to set
    */
   public void setNotes(String notes) {
      this.notes = notes;
   }

   
      
   
}

