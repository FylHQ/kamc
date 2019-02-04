package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;

@Entity(name="i3_tran_component")
public class I3TranComponent {

   @Column(name = "i3trn_tran_component_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3obj_object_id", nullable = false)
   private Long objObjectId;

   @Column(name = "i3cfv_classifier_value_id")
   private Long cfvClassifierValueId;

   @Column(name = "i3trn_state_num")
   private String trnStateNum;

   @Column(name = "i3trn_inventory_num")
   private String trnInventoryNum;

   @Column(name = "i3trn_pass_num")
   private String trnPassNum;

   @Column(name = "i3trn_pass_ser")
   private String trnPassSer;

   @Column(name = "i3trn_issue_year")
   private Long trnIssueYear;

   @Column(name = "i3trn_isfinish", nullable = false)
   private String trnIsfinish;

   @Column(name = "i3trn_category")
   private String trnCategory;

   @Column(name = "i3trn_vin")
   private String trnVin;

   @Column(name = "i3trn_svid_num")
   private String trnSvidNum;

   @Column(name = "i3trn_svid_ser")
   private String trnSvidSer;

   @Column(name = "i3trn_svid_date")
   private Date trnSvidDate;

   @Column(name = "i3trn_reg_notice")
   private String trnRegNotice;

   @Column(name = "i3trn_eng_model")
   private String trnEngModel;

   @Column(name = "i3trn_eng_num")
   private String trnEngNum;

   @Column(name = "i3trn_eng_power")
   private Long trnEngPower;

   @Column(name = "i3trn_eng_capacity")
   private Long trnEngCapacity;

   @Column(name = "i3trn_chassis_num")
   private String trnChassisNum;

   @Column(name = "i3trn_body_num")
   private String trnBodyNum;

   @Column(name = "i3trn_color")
   private String trnColor;

   @Column(name = "i3trn_mass_max")
   private Long trnMassMax;

   @Column(name = "i3trn_mass_unload")
   private Long trnMassUnload;

   @Column(name = "i3trn_description")
   private String trnDescription;

   @Column(name = "i3trn_reg_num", nullable = false)
   private Long trnRegNum;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getObjObjectId() {
      return objObjectId;
   }

   public void setObjObjectId(Long objObjectId) {
      this.objObjectId = objObjectId;
   }

   public Long getCfvClassifierValueId() {
      return cfvClassifierValueId;
   }

   public void setCfvClassifierValueId(Long cfvClassifierValueId) {
      this.cfvClassifierValueId = cfvClassifierValueId;
   }

   public String getTrnStateNum() {
      return trnStateNum;
   }

   public void setTrnStateNum(String trnStateNum) {
      this.trnStateNum = trnStateNum;
   }

   public String getTrnInventoryNum() {
      return trnInventoryNum;
   }

   public void setTrnInventoryNum(String trnInventoryNum) {
      this.trnInventoryNum = trnInventoryNum;
   }

   public String getTrnPassNum() {
      return trnPassNum;
   }

   public void setTrnPassNum(String trnPassNum) {
      this.trnPassNum = trnPassNum;
   }

   public String getTrnPassSer() {
      return trnPassSer;
   }

   public void setTrnPassSer(String trnPassSer) {
      this.trnPassSer = trnPassSer;
   }

   public Long getTrnIssueYear() {
      return trnIssueYear;
   }

   public void setTrnIssueYear(Long trnIssueYear) {
      this.trnIssueYear = trnIssueYear;
   }

   public String getTrnIsfinish() {
      return trnIsfinish;
   }

   public void setTrnIsfinish(String trnIsfinish) {
      this.trnIsfinish = trnIsfinish;
   }

   public String getTrnCategory() {
      return trnCategory;
   }

   public void setTrnCategory(String trnCategory) {
      this.trnCategory = trnCategory;
   }

   public String getTrnVin() {
      return trnVin;
   }

   public void setTrnVin(String trnVin) {
      this.trnVin = trnVin;
   }

   public String getTrnSvidNum() {
      return trnSvidNum;
   }

   public void setTrnSvidNum(String trnSvidNum) {
      this.trnSvidNum = trnSvidNum;
   }

   public String getTrnSvidSer() {
      return trnSvidSer;
   }

   public void setTrnSvidSer(String trnSvidSer) {
      this.trnSvidSer = trnSvidSer;
   }

   public Date getTrnSvidDate() {
      return trnSvidDate;
   }

   public void setTrnSvidDate(Date trnSvidDate) {
      this.trnSvidDate = trnSvidDate;
   }

   public String getTrnRegNotice() {
      return trnRegNotice;
   }

   public void setTrnRegNotice(String trnRegNotice) {
      this.trnRegNotice = trnRegNotice;
   }

   public String getTrnEngModel() {
      return trnEngModel;
   }

   public void setTrnEngModel(String trnEngModel) {
      this.trnEngModel = trnEngModel;
   }

   public String getTrnEngNum() {
      return trnEngNum;
   }

   public void setTrnEngNum(String trnEngNum) {
      this.trnEngNum = trnEngNum;
   }

   public Long getTrnEngPower() {
      return trnEngPower;
   }

   public void setTrnEngPower(Long trnEngPower) {
      this.trnEngPower = trnEngPower;
   }

   public Long getTrnEngCapacity() {
      return trnEngCapacity;
   }

   public void setTrnEngCapacity(Long trnEngCapacity) {
      this.trnEngCapacity = trnEngCapacity;
   }

   public String getTrnChassisNum() {
      return trnChassisNum;
   }

   public void setTrnChassisNum(String trnChassisNum) {
      this.trnChassisNum = trnChassisNum;
   }

   public String getTrnBodyNum() {
      return trnBodyNum;
   }

   public void setTrnBodyNum(String trnBodyNum) {
      this.trnBodyNum = trnBodyNum;
   }

   public String getTrnColor() {
      return trnColor;
   }

   public void setTrnColor(String trnColor) {
      this.trnColor = trnColor;
   }

   public Long getTrnMassMax() {
      return trnMassMax;
   }

   public void setTrnMassMax(Long trnMassMax) {
      this.trnMassMax = trnMassMax;
   }

   public Long getTrnMassUnload() {
      return trnMassUnload;
   }

   public void setTrnMassUnload(Long trnMassUnload) {
      this.trnMassUnload = trnMassUnload;
   }

   public String getTrnDescription() {
      return trnDescription;
   }

   public void setTrnDescription(String trnDescription) {
      this.trnDescription = trnDescription;
   }

   public Long getTrnRegNum() {
      return trnRegNum;
   }

   public void setTrnRegNum(Long trnRegNum) {
      this.trnRegNum = trnRegNum;
   }

}