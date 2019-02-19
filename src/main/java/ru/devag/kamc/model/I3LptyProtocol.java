package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="i3_lpty_protocol")
public class I3LptyProtocol {

   @Column(name = "i3ptl_protocol_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
   @JoinColumn(name = "i3lpy_lpty_component_id", nullable = false)
   private I3LptyComponent lpty;

   @Column(name = "i3sbb_sbj_bst_id")
   private Long sbbSbjBstId;

   @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
   @JoinColumn(name = "i3sbj_subject_id", nullable = false)
   private I3Subject sbj;

   @Column(name = "i3cfv_account_id")
   private Long cfvAccountId;

   @Column(name = "i3ptl_number")
   private String ptlNumber;

   @Column(name = "i3ptl_start_date", nullable = false)
   private Date ptlStartDate;

   @Column(name = "i3ptl_end_date", nullable = false)
   private Date ptlEndDate;

   @Column(name = "i3ptl_increase_coeff")
   private Double ptlIncreaseCoeff;

   @Column(name = "i3ptl_period_value")
   private Double ptlPeriodValue;

   @Column(name = "i3ptl_cad_cost")
   private Long ptlCadCost;

   @Column(name = "i3ptl_full_area")
   private Double ptlFullArea;

   @Column(name = "i3ptl_leaser_area")
   private Double ptlLeaserArea;

   @Column(name = "i3ptl_cad_coeff")
   private Double ptlCadCoeff;

   @Column(name = "i3ptl_lea_rate")
   private Double ptlLeaRate;

   @Column(name = "i3ptl_year_rate")
   private Double ptlYearRate;

   @Column(name = "i3ptl_period_rate")
   private Double ptlPeriodRate;

   @Column(name = "i3ptl_methodics")
   private Long ptlMethodics;

   @Column(name = "i3ptl_lea_zone")
   private String ptlLeaZone;

   @Column(name = "i3ptl_create_date")
   @CreationTimestamp
   private Date ptlCreateDate;

   @Column(name = "i3ptl_vat", nullable = false)
   private Double ptlVat;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public I3LptyComponent getLpty() {
      return lpty;
   }

   public void setLpty(I3LptyComponent lpty) {
      this.lpty = lpty;
   }

   public Long getSbbSbjBstId() {
      return sbbSbjBstId;
   }

   public void setSbbSbjBstId(Long sbbSbjBstId) {
      this.sbbSbjBstId = sbbSbjBstId;
   }

   public Long getCfvAccountId() {
      return cfvAccountId;
   }

   public void setCfvAccountId(Long cfvAccountId) {
      this.cfvAccountId = cfvAccountId;
   }

   public String getPtlNumber() {
      return ptlNumber;
   }

   public void setPtlNumber(String ptlNumber) {
      this.ptlNumber = ptlNumber;
   }

   public Date getPtlStartDate() {
      return ptlStartDate;
   }

   public void setPtlStartDate(Date ptlStartDate) {
      this.ptlStartDate = ptlStartDate;
   }

   public Date getPtlEndDate() {
      return ptlEndDate;
   }

   public void setPtlEndDate(Date ptlEndDate) {
      this.ptlEndDate = ptlEndDate;
   }

   public Double getPtlIncreaseCoeff() {
      return ptlIncreaseCoeff;
   }

   public void setPtlIncreaseCoeff(Double ptlIncreaseCoeff) {
      this.ptlIncreaseCoeff = ptlIncreaseCoeff;
   }

   public Double getPtlPeriodValue() {
      return ptlPeriodValue;
   }

   public void setPtlPeriodValue(Double ptlPeriodValue) {
      this.ptlPeriodValue = ptlPeriodValue;
   }

   public Long getPtlCadCost() {
      return ptlCadCost;
   }

   public void setPtlCadCost(Long ptlCadCost) {
      this.ptlCadCost = ptlCadCost;
   }

   public Double getPtlFullArea() {
      return ptlFullArea;
   }

   public void setPtlFullArea(Double ptlFullArea) {
      this.ptlFullArea = ptlFullArea;
   }

   public Double getPtlLeaserArea() {
      return ptlLeaserArea;
   }

   public void setPtlLeaserArea(Double ptlLeaserArea) {
      this.ptlLeaserArea = ptlLeaserArea;
   }

   public Double getPtlCadCoeff() {
      return ptlCadCoeff;
   }

   public void setPtlCadCoeff(Double ptlCadCoeff) {
      this.ptlCadCoeff = ptlCadCoeff;
   }

   public Double getPtlLeaRate() {
      return ptlLeaRate;
   }

   public void setPtlLeaRate(Double ptlLeaRate) {
      this.ptlLeaRate = ptlLeaRate;
   }

   public Double getPtlYearRate() {
      return ptlYearRate;
   }

   public void setPtlYearRate(Double ptlYearRate) {
      this.ptlYearRate = ptlYearRate;
   }

   public Double getPtlPeriodRate() {
      return ptlPeriodRate;
   }

   public void setPtlPeriodRate(Double ptlPeriodRate) {
      this.ptlPeriodRate = ptlPeriodRate;
   }

   public Long getPtlMethodics() {
      return ptlMethodics;
   }

   public void setPtlMethodics(Long ptlMethodics) {
      this.ptlMethodics = ptlMethodics;
   }

   public String getPtlLeaZone() {
      return ptlLeaZone;
   }

   public void setPtlLeaZone(String ptlLeaZone) {
      this.ptlLeaZone = ptlLeaZone;
   }

   public Date getPtlCreateDate() {
      return ptlCreateDate;
   }

   public void setPtlCreateDate(Date ptlCreateDate) {
      this.ptlCreateDate = ptlCreateDate;
   }

   public Double getPtlVat() {
      return ptlVat;
   }

   public void setPtlVat(Double ptlVat) {
      this.ptlVat = ptlVat;
   }

   public I3Subject getSbj() {
      return sbj;
   }

   public void setSbj(I3Subject sbj) {
      this.sbj = sbj;
   }

}