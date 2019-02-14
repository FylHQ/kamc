package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="i3_aprm_component")
public class I3AprmComponent {

   @Column(name = "i3apm_aprm_component_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
   @JoinColumn(name = "i3obj_object_id", nullable = false)
   private I3Object object;

   @Column(name = "i3apm_common_area")
   private Double apmCommonArea;

   @Column(name = "i3apm_living_area")
   private Double apmLivingArea;

   @Column(name = "i3apm_floor")
   private Long apmFloor;

   @Column(name = "i3apm_plan_number")
   private String apmPlanNumber;

   @Column(name = "i3apm_store_area")
   private Double apmStoreArea;

   @Column(name = "i3cfv_classifier_value_id", nullable = false)
   private Long cfvClassifierValueId;

   @Column(name = "i3apm_description")
   private String apmDescription;

   @Column(name = "i3apm_isfinish", nullable = false)
   private String apmIsfinish;

   @Column(name = "i3apm_quarters")
   private Long apmQuarters;

   @Column(name = "i3apm_usage")
   private String apmUsage;

   @Column(name = "i3apm_purpose")
   private String apmPurpose;

   @Column(name = "i3apm_is_part", nullable = false)
   private String apmIsPart;

   @Column(name = "i3apm_part_type_id")
   private Long apmPartTypeId;

   @Column(name = "i3apm_construct_period")
   private String apmConstructPeriod;

   @Column(name = "i3apm_implement_date")
   private Date apmImplementDate;

   @Column(name = "i3apm_bti_inv_number")
   private String apmBtiInvNumber;

   @Column(name = "i3apm_bti_date")
   private Date apmBtiDate;

   @Column(name = "i3apm_bti_letter")
   private String apmBtiLetter;

   @Column(name = "i3apm_public_area")
   private Double apmPublicArea;

   @Column(name = "i3apm_change_doc_descr")
   private String apmChangeDocDescr;

   @Column(name = "i3apm_change_doc_num")
   private String apmChangeDocNum;

   @Column(name = "i3apm_change_doc_date")
   private Date apmChangeDocDate;

   @Column(name = "i3apm_krz_clf_id")
   private Long apmKrzClfId;

   @Column(name = "i3apm_kki_clf_id")
   private Long apmKkiClfId;

   @Column(name = "i3apm_cadastral_info")
   private String apmCadastralInfo;

   @Column(name = "i3apm_cadastral_cost")
   private Double apmCadastralCost;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Double getApmCommonArea() {
      return apmCommonArea;
   }

   public void setApmCommonArea(Double apmCommonArea) {
      this.apmCommonArea = apmCommonArea;
   }

   public Double getApmLivingArea() {
      return apmLivingArea;
   }

   public void setApmLivingArea(Double apmLivingArea) {
      this.apmLivingArea = apmLivingArea;
   }

   public Long getApmFloor() {
      return apmFloor;
   }

   public void setApmFloor(Long apmFloor) {
      this.apmFloor = apmFloor;
   }

   public String getApmPlanNumber() {
      return apmPlanNumber;
   }

   public void setApmPlanNumber(String apmPlanNumber) {
      this.apmPlanNumber = apmPlanNumber;
   }

   public Double getApmStoreArea() {
      return apmStoreArea;
   }

   public void setApmStoreArea(Double apmStoreArea) {
      this.apmStoreArea = apmStoreArea;
   }

   public Long getCfvClassifierValueId() {
      return cfvClassifierValueId;
   }

   public void setCfvClassifierValueId(Long cfvClassifierValueId) {
      this.cfvClassifierValueId = cfvClassifierValueId;
   }

   public String getApmDescription() {
      return apmDescription;
   }

   public void setApmDescription(String apmDescription) {
      this.apmDescription = apmDescription;
   }

   public String getApmIsfinish() {
      return apmIsfinish;
   }

   public void setApmIsfinish(String apmIsfinish) {
      this.apmIsfinish = apmIsfinish;
   }

   public Long getApmQuarters() {
      return apmQuarters;
   }

   public void setApmQuarters(Long apmQuarters) {
      this.apmQuarters = apmQuarters;
   }

   public String getApmUsage() {
      return apmUsage;
   }

   public void setApmUsage(String apmUsage) {
      this.apmUsage = apmUsage;
   }

   public String getApmPurpose() {
      return apmPurpose;
   }

   public void setApmPurpose(String apmPurpose) {
      this.apmPurpose = apmPurpose;
   }

   public String getApmIsPart() {
      return apmIsPart;
   }

   public void setApmIsPart(String apmIsPart) {
      this.apmIsPart = apmIsPart;
   }

   public Long getApmPartTypeId() {
      return apmPartTypeId;
   }

   public void setApmPartTypeId(Long apmPartTypeId) {
      this.apmPartTypeId = apmPartTypeId;
   }

   public String getApmConstructPeriod() {
      return apmConstructPeriod;
   }

   public void setApmConstructPeriod(String apmConstructPeriod) {
      this.apmConstructPeriod = apmConstructPeriod;
   }

   public Date getApmImplementDate() {
      return apmImplementDate;
   }

   public void setApmImplementDate(Date apmImplementDate) {
      this.apmImplementDate = apmImplementDate;
   }

   public String getApmBtiInvNumber() {
      return apmBtiInvNumber;
   }

   public void setApmBtiInvNumber(String apmBtiInvNumber) {
      this.apmBtiInvNumber = apmBtiInvNumber;
   }

   public Date getApmBtiDate() {
      return apmBtiDate;
   }

   public void setApmBtiDate(Date apmBtiDate) {
      this.apmBtiDate = apmBtiDate;
   }

   public String getApmBtiLetter() {
      return apmBtiLetter;
   }

   public void setApmBtiLetter(String apmBtiLetter) {
      this.apmBtiLetter = apmBtiLetter;
   }

   public Double getApmPublicArea() {
      return apmPublicArea;
   }

   public void setApmPublicArea(Double apmPublicArea) {
      this.apmPublicArea = apmPublicArea;
   }

   public String getApmChangeDocDescr() {
      return apmChangeDocDescr;
   }

   public void setApmChangeDocDescr(String apmChangeDocDescr) {
      this.apmChangeDocDescr = apmChangeDocDescr;
   }

   public String getApmChangeDocNum() {
      return apmChangeDocNum;
   }

   public void setApmChangeDocNum(String apmChangeDocNum) {
      this.apmChangeDocNum = apmChangeDocNum;
   }

   public Date getApmChangeDocDate() {
      return apmChangeDocDate;
   }

   public void setApmChangeDocDate(Date apmChangeDocDate) {
      this.apmChangeDocDate = apmChangeDocDate;
   }

   public Long getApmKrzClfId() {
      return apmKrzClfId;
   }

   public void setApmKrzClfId(Long apmKrzClfId) {
      this.apmKrzClfId = apmKrzClfId;
   }

   public Long getApmKkiClfId() {
      return apmKkiClfId;
   }

   public void setApmKkiClfId(Long apmKkiClfId) {
      this.apmKkiClfId = apmKkiClfId;
   }

   public String getApmCadastralInfo() {
      return apmCadastralInfo;
   }

   public void setApmCadastralInfo(String apmCadastralInfo) {
      this.apmCadastralInfo = apmCadastralInfo;
   }

   public Double getApmCadastralCost() {
      return apmCadastralCost;
   }

   public void setApmCadastralCost(Double apmCadastralCost) {
      this.apmCadastralCost = apmCadastralCost;
   }

   public I3Object getObject() {
      return object;
   }

   public void setObject(I3Object object) {
      this.object = object;
   }

}