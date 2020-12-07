package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="i3_paym_component")
public class I3PaymComponent {

   @Column(name = "i3pac_paym_component_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
   @JoinColumn(name = "i3bst_basement_id", nullable = false)
   private I3Basement bst;

   @Column(name = "i3pac_number")
   private String pacNumber;

   @Column(name = "i3pac_start_date", nullable = false)
   private Date pacStartDate;

   @Column(name = "i3pac_end_date", nullable = false)
   private Date pacEndDate;

   @Column(name = "i3pac_increase_coeff")
   private Double pacIncreaseCoeff;

   @Column(name = "i3pac_leaser_part")
   private Double pacLeaserPart;

   @Column(name = "i3cfv_classifier_value_id")
   private Long cfvClassifierValueId;

   @Column(name = "i3pac_account_id")
   private Long pacAccountId;

   @Column(name = "i3pac_lea_rate")
   private Double pacLeaRate;

   @Column(name = "i3pac_period_value")
   private Double pacPeriodValue;

   @Column(name = "i3pac_cad_cost")
   private Long pacCadCost;

   @Column(name = "i3pac_full_area")
   private Double pacFullArea;

   @Column(name = "i3pac_year_rate")
   private Double pacYearRate;

   @Column(name = "i3pac_period_rate")
   private Double pacPeriodRate;

   @Column(name = "i3pac_methodics")
   private Long pacMethodics;

   @Column(name = "i3pac_lea_zone")
   private String pacLeaZone;

   @Column(name = "i3obj_object_id")
   private Long objObjectId;

   @Column(name = "i3pac_used_algorithm")
   private Long pacUsedAlgorithm;

   @Column(name = "i3pac_parent_id")
   private Long pacParentId;

   @Column(name = "i3pac_base_cost")
   private Long pacBaseCost;

   @Column(name = "i3pac_usepercrate")
   private String pacUsepercrate;

   @Column(name = "i3pac_feerate")
   private Double pacFeerate;

   @Column(name = "i3pac_cad_coeff")
   private Double pacCadCoeff;

   @Column(name = "i3pac_obj_count", nullable = false)
   private Long pacObjCount;

   @Column(name = "i3pac_sbj_description")
   private String pacSbjDescription;

   @Column(name = "i3pac_sbj_sm_business_type")
   private String pacSbjSmBusinessType;

   @Column(name = "i3pac_sbj_sm_business_reg_date")
   private Date pacSbjSmBusinessRegDate;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getPacNumber() {
      return pacNumber;
   }

   public void setPacNumber(String pacNumber) {
      this.pacNumber = pacNumber;
   }

   public Date getPacStartDate() {
      return pacStartDate;
   }

   public void setPacStartDate(Date pacStartDate) {
      this.pacStartDate = pacStartDate;
   }

   public Date getPacEndDate() {
      return pacEndDate;
   }

   public void setPacEndDate(Date pacEndDate) {
      this.pacEndDate = pacEndDate;
   }

   public Double getPacIncreaseCoeff() {
      return pacIncreaseCoeff;
   }

   public void setPacIncreaseCoeff(Double pacIncreaseCoeff) {
      this.pacIncreaseCoeff = pacIncreaseCoeff;
   }

   public Double getPacLeaserPart() {
      return pacLeaserPart;
   }

   public void setPacLeaserPart(Double pacLeaserPart) {
      this.pacLeaserPart = pacLeaserPart;
   }

   public Long getCfvClassifierValueId() {
      return cfvClassifierValueId;
   }

   public void setCfvClassifierValueId(Long cfvClassifierValueId) {
      this.cfvClassifierValueId = cfvClassifierValueId;
   }

   public Long getPacAccountId() {
      return pacAccountId;
   }

   public void setPacAccountId(Long pacAccountId) {
      this.pacAccountId = pacAccountId;
   }

   public Double getPacLeaRate() {
      return pacLeaRate;
   }

   public void setPacLeaRate(Double pacLeaRate) {
      this.pacLeaRate = pacLeaRate;
   }

   public Double getPacPeriodValue() {
      return pacPeriodValue;
   }

   public void setPacPeriodValue(Double pacPeriodValue) {
      this.pacPeriodValue = pacPeriodValue;
   }

   public Long getPacCadCost() {
      return pacCadCost;
   }

   public void setPacCadCost(Long pacCadCost) {
      this.pacCadCost = pacCadCost;
   }

   public Double getPacFullArea() {
      return pacFullArea;
   }

   public void setPacFullArea(Double pacFullArea) {
      this.pacFullArea = pacFullArea;
   }

   public Double getPacYearRate() {
      return pacYearRate;
   }

   public void setPacYearRate(Double pacYearRate) {
      this.pacYearRate = pacYearRate;
   }

   public Double getPacPeriodRate() {
      return pacPeriodRate;
   }

   public void setPacPeriodRate(Double pacPeriodRate) {
      this.pacPeriodRate = pacPeriodRate;
   }

   public Long getPacMethodics() {
      return pacMethodics;
   }

   public void setPacMethodics(Long pacMethodics) {
      this.pacMethodics = pacMethodics;
   }

   public String getPacLeaZone() {
      return pacLeaZone;
   }

   public void setPacLeaZone(String pacLeaZone) {
      this.pacLeaZone = pacLeaZone;
   }

   public Long getObjObjectId() {
      return objObjectId;
   }

   public void setObjObjectId(Long objObjectId) {
      this.objObjectId = objObjectId;
   }

   public Long getPacUsedAlgorithm() {
      return pacUsedAlgorithm;
   }

   public void setPacUsedAlgorithm(Long pacUsedAlgorithm) {
      this.pacUsedAlgorithm = pacUsedAlgorithm;
   }

   public Long getPacParentId() {
      return pacParentId;
   }

   public void setPacParentId(Long pacParentId) {
      this.pacParentId = pacParentId;
   }

   public Long getPacBaseCost() {
      return pacBaseCost;
   }

   public void setPacBaseCost(Long pacBaseCost) {
      this.pacBaseCost = pacBaseCost;
   }

   public String getPacUsepercrate() {
      return pacUsepercrate;
   }

   public void setPacUsepercrate(String pacUsepercrate) {
      this.pacUsepercrate = pacUsepercrate;
   }

   public Double getPacFeerate() {
      return pacFeerate;
   }

   public void setPacFeerate(Double pacFeerate) {
      this.pacFeerate = pacFeerate;
   }

   public Double getPacCadCoeff() {
      return pacCadCoeff;
   }

   public void setPacCadCoeff(Double pacCadCoeff) {
      this.pacCadCoeff = pacCadCoeff;
   }

   public Long getPacObjCount() {
      return pacObjCount;
   }

   public void setPacObjCount(Long pacObjCount) {
      this.pacObjCount = pacObjCount;
   }

   public String getPacSbjDescription() {
      return pacSbjDescription;
   }

   public void setPacSbjDescription(String pacSbjDescription) {
      this.pacSbjDescription = pacSbjDescription;
   }

   public String getPacSbjSmBusinessType() {
      return pacSbjSmBusinessType;
   }

   public void setPacSbjSmBusinessType(String pacSbjSmBusinessType) {
      this.pacSbjSmBusinessType = pacSbjSmBusinessType;
   }

   public Date getPacSbjSmBusinessRegDate() {
      return pacSbjSmBusinessRegDate;
   }

   public void setPacSbjSmBusinessRegDate(Date pacSbjSmBusinessRegDate) {
      this.pacSbjSmBusinessRegDate = pacSbjSmBusinessRegDate;
   }

   public I3Basement getBst() {
      return bst;
   }

   public void setBst(I3Basement bst) {
      this.bst = bst;
   }
}