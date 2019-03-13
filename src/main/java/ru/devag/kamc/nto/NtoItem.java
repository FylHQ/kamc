package ru.devag.kamc.nto;

import java.util.Date;
import ru.devag.kamc.CellIndex;

public class NtoItem {
   @CellIndex("№ договора")
   Integer cntrNum;

   @CellIndex("Статус")
   String status;
   
   @CellIndex("Дата заключения договора")
   Date cntrConfirmDate;
   
   @CellIndex("Фамилия, имя, отчество. Название предприятия")
   String sbj;
   
   @CellIndex("Дополнительные соглашения")
   String cntrExtra1;
   
   @CellIndex("Доп. Соглашения к договорам")
   String cntrExtra2;
   
   @CellIndex("Место размещения объекта")
   String placement;
   
   @CellIndex("Строка по схеме")
   String scheme;
   
   @CellIndex(value = "Строка по схеме", offset = 1)
   Date cntrStartDate;
   
   @CellIndex(value = "Строка по схеме", offset = 2)
   Date cntrEndDate;
   
   @CellIndex("Кадастровый квартал")
   Integer cadBlock;
   
   @CellIndex("Тип объекта")
   String objType;
   
   @CellIndex("Специализация")
   String spec;
   
   @CellIndex("Ccp")
   Double cadCostAVG;
   
   @CellIndex("площадь объекта")
   Double area;
   
   @CellIndex("Ктип")
   Integer kioskType;
   
   @CellIndex("Начальная цена по нашему")
   Double startPrice;
   
   @CellIndex("Кол-во дней периода раземещения объекта, ед. в 2018 году")
   Integer daysYear;
   
   @CellIndex("Коэффициент инфляции на 2018 год")
   Double coeff;
   
   @CellIndex("Плата по договору январь -декабрь 2018 год")
   Double costYear;
   
   @CellIndex("количество дней с мая по декабрь")
   Integer daysMayDec;
   
   @CellIndex("Плата по договору май -декабрь 2018 год")
   Double costMayDec;
   
   @CellIndex("город")
   String city;
   
   @CellIndex("улица")
   String street;
   
   @CellIndex("дом")
   String house;
   
   @CellIndex("квартира")
   String flat;
   
   @CellIndex("Телефон")
   String phone;

   /**
    * @return the cntrNum
    */
   public Integer getCntrNum() {
      return cntrNum;
   }

   /**
    * @param cntrNum the cntrNum to set
    */
   public void setCntrNum(Integer cntrNum) {
      this.cntrNum = cntrNum;
   }

   /**
    * @return the status
    */
   public String getStatus() {
      return status;
   }

   /**
    * @param status the status to set
    */
   public void setStatus(String status) {
      this.status = status;
   }

   /**
    * @return the cntrConfirmDate
    */
   public Date getCntrConfirmDate() {
      return cntrConfirmDate;
   }

   /**
    * @param cntrConfirmDate the cntrConfirmDate to set
    */
   public void setCntrConfirmDate(Date cntrConfirmDate) {
      this.cntrConfirmDate = cntrConfirmDate;
   }

   /**
    * @return the sbj
    */
   public String getSbj() {
      return sbj;
   }

   /**
    * @param sbj the sbj to set
    */
   public void setSbj(String sbj) {
      this.sbj = sbj;
   }

   /**
    * @return the cntrExtra1
    */
   public String getCntrExtra1() {
      return cntrExtra1;
   }

   /**
    * @param cntrExtra1 the cntrExtra1 to set
    */
   public void setCntrExtra1(String cntrExtra1) {
      this.cntrExtra1 = cntrExtra1;
   }

   /**
    * @return the cntrExtra2
    */
   public String getCntrExtra2() {
      return cntrExtra2;
   }

   /**
    * @param cntrExtra2 the cntrExtra2 to set
    */
   public void setCntrExtra2(String cntrExtra2) {
      this.cntrExtra2 = cntrExtra2;
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
    * @return the scheme
    */
   public String getScheme() {
      return scheme;
   }

   /**
    * @param scheme the scheme to set
    */
   public void setScheme(String scheme) {
      this.scheme = scheme;
   }

   /**
    * @return the cntrStartDate
    */
   public Date getCntrStartDate() {
      return cntrStartDate;
   }

   /**
    * @param cntrStartDate the cntrStartDate to set
    */
   public void setCntrStartDate(Date cntrStartDate) {
      this.cntrStartDate = cntrStartDate;
   }

   /**
    * @return the cntrEndDate
    */
   public Date getCntrEndDate() {
      return cntrEndDate;
   }

   /**
    * @param cntrEndDate the cntrEndDate to set
    */
   public void setCntrEndDate(Date cntrEndDate) {
      this.cntrEndDate = cntrEndDate;
   }

   /**
    * @return the cadBlock
    */
   public Integer getCadBlock() {
      return cadBlock;
   }

   /**
    * @param cadBlock the cadBlock to set
    */
   public void setCadBlock(Integer cadBlock) {
      this.cadBlock = cadBlock;
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
    * @return the spec
    */
   public String getSpec() {
      return spec;
   }

   /**
    * @param spec the spec to set
    */
   public void setSpec(String spec) {
      this.spec = spec;
   }

   /**
    * @return the cadCostAVG
    */
   public Double getCadCostAVG() {
      return cadCostAVG;
   }

   /**
    * @param cadCostAVG the cadCostAVG to set
    */
   public void setCadCostAVG(Double cadCostAVG) {
      this.cadCostAVG = cadCostAVG;
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
    * @return the kioskType
    */
   public Integer getKioskType() {
      return kioskType;
   }

   /**
    * @param kioskType the kioskType to set
    */
   public void setKioskType(Integer kioskType) {
      this.kioskType = kioskType;
   }

   /**
    * @return the startPrice
    */
   public Double getStartPrice() {
      return startPrice;
   }

   /**
    * @param startPrice the startPrice to set
    */
   public void setStartPrice(Double startPrice) {
      this.startPrice = startPrice;
   }

   /**
    * @return the daysYear
    */
   public Integer getDaysYear() {
      return daysYear;
   }

   /**
    * @param daysYear the daysYear to set
    */
   public void setDaysYear(Integer daysYear) {
      this.daysYear = daysYear;
   }

   /**
    * @return the coeff
    */
   public Double getCoeff() {
      return coeff;
   }

   /**
    * @param coeff the coeff to set
    */
   public void setCoeff(Double coeff) {
      this.coeff = coeff;
   }

   /**
    * @return the costYear
    */
   public Double getCostYear() {
      return costYear;
   }

   /**
    * @param costYear the costYear to set
    */
   public void setCostYear(Double costYear) {
      this.costYear = costYear;
   }

   /**
    * @return the daysMayDec
    */
   public Integer getDaysMayDec() {
      return daysMayDec;
   }

   /**
    * @param daysMayDec the daysMayDec to set
    */
   public void setDaysMayDec(Integer daysMayDec) {
      this.daysMayDec = daysMayDec;
   }

   /**
    * @return the costMayDec
    */
   public Double getCostMayDec() {
      return costMayDec;
   }

   /**
    * @param costMayDec the costMayDec to set
    */
   public void setCostMayDec(Double costMayDec) {
      this.costMayDec = costMayDec;
   }

   /**
    * @return the city
    */
   public String getCity() {
      return city;
   }

   /**
    * @param city the city to set
    */
   public void setCity(String city) {
      this.city = city;
   }

   /**
    * @return the street
    */
   public String getStreet() {
      return street;
   }

   /**
    * @param street the street to set
    */
   public void setStreet(String street) {
      this.street = street;
   }

   /**
    * @return the house
    */
   public String getHouse() {
      return house;
   }

   /**
    * @param house the house to set
    */
   public void setHouse(String house) {
      this.house = house;
   }

   /**
    * @return the flat
    */
   public String getFlat() {
      return flat;
   }

   /**
    * @param flat the flat to set
    */
   public void setFlat(String flat) {
      this.flat = flat;
   }

   /**
    * @return the phone
    */
   public String getPhone() {
      return phone;
   }

   /**
    * @param phone the phone to set
    */
   public void setPhone(String phone) {
      this.phone = phone;
   }

   

}

