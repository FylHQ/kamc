package ru.devag.kamc.nto;

import java.util.Date;
import ru.devag.kamc.CellIndex;

public class NtoItem {
   Integer rowId;
   
   @CellIndex("№ договора")
   Integer cntrNum;

   @CellIndex("Дата заключения договора")
   Date confirmDate;
   
   @CellIndex("дата начала платежей")
   Date payStartDate;

   @CellIndex("дата окончания действия договора")
   Date endDate;

   //предыдущая дата окончания действия договора
   @CellIndex(value = "дата окончания действия договора", offset = 1)
   Date prevEndDate;

   @CellIndex("Преимущ право (номер дата)")
   String rightsNumAndDate;

   @CellIndex("ФИО. Название предприятия")
   String sbj;
   
   @CellIndex("Дата выдачи архитектурных решений")
   Date architectDate;

   @CellIndex("Наличие и дата согласования колерного паспорта/отсутствие колерного паспорта*")
   String colorPassport;

   @CellIndex("Примечание")
   String notes;

   @CellIndex("Представитель")
   String agent;

   @CellIndex("ИНН")
   String inn;

   @CellIndex("Адрес места нахождения юридического лица индивидуального предпринимателя")
   String address;

   @CellIndex("Телефон")
   String phone;

   //ФИО. Название предприятия. Предыдущие
   @CellIndex(value = "Телефон", offset = 1)
   String prevSbj;

   //Адрес  Предыдущего
   @CellIndex(value = "Телефон", offset = 2)
   String prevAddress;

   @CellIndex("Тел Предыдущего")
   String prevPhone;

   //ФИО. Название предприятия. Предыдущие
   @CellIndex(value = "Тел Предыдущего", offset = 1)
   String prevPrevSbj;
   
   //Адрес  Предыдущего
   @CellIndex(value = "Тел Предыдущего", offset = 2)
   String prevPrevAddress;

   //Тел Предыдущего
   @CellIndex(value = "Тел Предыдущего", offset = 3)
   String prevPrevPhone;

   @CellIndex("Доп. Соглашения к договорам")
   String cntrExtra;

   @CellIndex("Место размещения объекта")
   String placement;
   
   @CellIndex("Строка по схеме (актуальный номер)")
   Integer scheme;

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
   
   @CellIndex("Количество дней в 2019 году")
   Integer daysYear;

   @CellIndex("Коэффициент инфляции на 2019 год")
   Double coeff;

   @CellIndex("Плата по договору  2019 год")
   Double costYear;

   @CellIndex("Формула 2019")
   Double formulaCostYear;

   //S                          площадь объекта,                   кв м 2018
   @CellIndex(value = "Формула 2019", offset = 1)
   Double area2018;

   @CellIndex("Кол-во дней периода раземещения объекта, ед. в 2018 году (с мая по декабрь)")
   Integer daysYear2018;

   @CellIndex("Коэффициент инфляции на 2018 год")
   Double coeff2018;

   @CellIndex("Плата по договору май -декабрь 2018 год")
   Double costYear2018;

   @CellIndex("Формула 2018 май-дек")
   Double formulaCostYear2018;

   /**
    * @return the rowId
    */
   public Integer getRowId() {
      return rowId;
   }

   /**
    * @param rowId the rowId to set
    */
   public void setRowId(Integer rowId) {
      this.rowId = rowId;
   }

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
    * @return the confirmDate
    */
   public Date getConfirmDate() {
      return confirmDate;
   }

   /**
    * @param confirmDate the confirmDate to set
    */
   public void setConfirmDate(Date confirmDate) {
      this.confirmDate = confirmDate;
   }

   /**
    * @return the payStartDate
    */
   public Date getPayStartDate() {
      return payStartDate;
   }

   /**
    * @param payStartDate the payStartDate to set
    */
   public void setPayStartDate(Date payStartDate) {
      this.payStartDate = payStartDate;
   }

   /**
    * @return the endDate
    */
   public Date getEndDate() {
      return endDate;
   }

   /**
    * @param endDate the endDate to set
    */
   public void setEndDate(Date endDate) {
      this.endDate = endDate;
   }

   /**
    * @return the prevEndDate
    */
   public Date getPrevEndDate() {
      return prevEndDate;
   }

   /**
    * @param prevEndDate the prevEndDate to set
    */
   public void setPrevEndDate(Date prevEndDate) {
      this.prevEndDate = prevEndDate;
   }

   /**
    * @return the rightsNumAndDate
    */
   public String getRightsNumAndDate() {
      return rightsNumAndDate;
   }

   /**
    * @param rightsNumAndDate the rightsNumAndDate to set
    */
   public void setRightsNumAndDate(String rightsNumAndDate) {
      this.rightsNumAndDate = rightsNumAndDate;
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
    * @return the architectDate
    */
   public Date getArchitectDate() {
      return architectDate;
   }

   /**
    * @param architectDate the architectDate to set
    */
   public void setArchitectDate(Date architectDate) {
      this.architectDate = architectDate;
   }

   /**
    * @return the colorPassport
    */
   public String getColorPassport() {
      return colorPassport;
   }

   /**
    * @param colorPassport the colorPassport to set
    */
   public void setColorPassport(String colorPassport) {
      this.colorPassport = colorPassport;
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

   /**
    * @return the agent
    */
   public String getAgent() {
      return agent;
   }

   /**
    * @param agent the agent to set
    */
   public void setAgent(String agent) {
      this.agent = agent;
   }

   /**
    * @return the inn
    */
   public String getInn() {
      return inn;
   }

   /**
    * @param inn the inn to set
    */
   public void setInn(String inn) {
      this.inn = inn;
   }

   /**
    * @return the address
    */
   public String getAddress() {
      return address;
   }

   /**
    * @param address the address to set
    */
   public void setAddress(String address) {
      this.address = address;
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

   /**
    * @return the prevSbj
    */
   public String getPrevSbj() {
      return prevSbj;
   }

   /**
    * @param prevSbj the prevSbj to set
    */
   public void setPrevSbj(String prevSbj) {
      this.prevSbj = prevSbj;
   }

   /**
    * @return the prevAddress
    */
   public String getPrevAddress() {
      return prevAddress;
   }

   /**
    * @param prevAddress the prevAddress to set
    */
   public void setPrevAddress(String prevAddress) {
      this.prevAddress = prevAddress;
   }

   /**
    * @return the prevPhone
    */
   public String getPrevPhone() {
      return prevPhone;
   }

   /**
    * @param prevPhone the prevPhone to set
    */
   public void setPrevPhone(String prevPhone) {
      this.prevPhone = prevPhone;
   }

   /**
    * @return the prevPrevSbj
    */
   public String getPrevPrevSbj() {
      return prevPrevSbj;
   }

   /**
    * @param prevPrevSbj the prevPrevSbj to set
    */
   public void setPrevPrevSbj(String prevPrevSbj) {
      this.prevPrevSbj = prevPrevSbj;
   }

   /**
    * @return the prevPrevAddress
    */
   public String getPrevPrevAddress() {
      return prevPrevAddress;
   }

   /**
    * @param prevPrevAddress the prevPrevAddress to set
    */
   public void setPrevPrevAddress(String prevPrevAddress) {
      this.prevPrevAddress = prevPrevAddress;
   }

   /**
    * @return the prevPrevPhone
    */
   public String getPrevPrevPhone() {
      return prevPrevPhone;
   }

   /**
    * @param prevPrevPhone the prevPrevPhone to set
    */
   public void setPrevPrevPhone(String prevPrevPhone) {
      this.prevPrevPhone = prevPrevPhone;
   }

   /**
    * @return the cntrExtra
    */
   public String getCntrExtra() {
      return cntrExtra;
   }

   /**
    * @param cntrExtra the cntrExtra to set
    */
   public void setCntrExtra(String cntrExtra) {
      this.cntrExtra = cntrExtra;
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
   public Integer getScheme() {
      return scheme;
   }

   /**
    * @param scheme the scheme to set
    */
   public void setScheme(Integer scheme) {
      this.scheme = scheme;
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
    * @return the formulaCostYear
    */
   public Double getFormulaCostYear() {
      return formulaCostYear;
   }

   /**
    * @param formulaCostYear the formulaCostYear to set
    */
   public void setFormulaCostYear(Double formulaCostYear) {
      this.formulaCostYear = formulaCostYear;
   }

   /**
    * @return the area2018
    */
   public Double getArea2018() {
      return area2018;
   }

   /**
    * @param area2018 the area2018 to set
    */
   public void setArea2018(Double area2018) {
      this.area2018 = area2018;
   }

   /**
    * @return the daysYear2018
    */
   public Integer getDaysYear2018() {
      return daysYear2018;
   }

   /**
    * @param daysYear2018 the daysYear2018 to set
    */
   public void setDaysYear2018(Integer daysYear2018) {
      this.daysYear2018 = daysYear2018;
   }

   /**
    * @return the coeff2018
    */
   public Double getCoeff2018() {
      return coeff2018;
   }

   /**
    * @param coeff2018 the coeff2018 to set
    */
   public void setCoeff2018(Double coeff2018) {
      this.coeff2018 = coeff2018;
   }

   /**
    * @return the costYear2018
    */
   public Double getCostYear2018() {
      return costYear2018;
   }

   /**
    * @param costYear2018 the costYear2018 to set
    */
   public void setCostYear2018(Double costYear2018) {
      this.costYear2018 = costYear2018;
   }

   /**
    * @return the formulaCostYear2018
    */
   public Double getFormulaCostYear2018() {
      return formulaCostYear2018;
   }

   /**
    * @param formulaCostYear2018 the formulaCostYear2018 to set
    */
   public void setFormulaCostYear2018(Double formulaCostYear2018) {
      this.formulaCostYear2018 = formulaCostYear2018;
   }
   
   
   

}

