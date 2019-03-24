package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="i3_nsto_protocol")
public class I3NstoProtocol {

   @Column(name = "i3ptl_protocol_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3nso_nsto_component_id", nullable = false)
   private Long nsoNstoComponentId;

   @Column(name = "i3sbb_sbj_bst_id")
   private Long sbbSbjBstId;

   @Column(name = "i3sbj_subject_id", nullable = false)
   private Long sbjSubjectId;

   @Column(name = "i3ptl_start_date", nullable = false)
   private Date ptlStartDate;

   @Column(name = "i3ptl_end_date", nullable = false)
   private Date ptlEndDate;

   @Column(name = "i3ptl_period_rate")
   private Double ptlPeriodRate;

   @Column(name = "i3ptl_year_rate")
   private Double ptlYearRate;

   @Column(name = "i3ptl_area")
   private Double ptlArea;

   @Column(name = "i3ptl_period")
   private Long ptlPeriod;

   @Column(name = "i3ptl_description")
   private String ptlDescription;

   @Column(name = "i3ptl_methodics")
   private Long ptlMethodics;

   @Column(name = "i3ptl_create_date", nullable = false)
   private Date ptlCreateDate;

   @Column(name = "i3obj_object_id", nullable = false)
   private Long objObjectId;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getNsoNstoComponentId() {
      return nsoNstoComponentId;
   }

   public void setNsoNstoComponentId(Long nsoNstoComponentId) {
      this.nsoNstoComponentId = nsoNstoComponentId;
   }

   public Long getSbbSbjBstId() {
      return sbbSbjBstId;
   }

   public void setSbbSbjBstId(Long sbbSbjBstId) {
      this.sbbSbjBstId = sbbSbjBstId;
   }

   public Long getSbjSubjectId() {
      return sbjSubjectId;
   }

   public void setSbjSubjectId(Long sbjSubjectId) {
      this.sbjSubjectId = sbjSubjectId;
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

   public Double getPtlPeriodRate() {
      return ptlPeriodRate;
   }

   public void setPtlPeriodRate(Double ptlPeriodRate) {
      this.ptlPeriodRate = ptlPeriodRate;
   }

   public Double getPtlYearRate() {
      return ptlYearRate;
   }

   public void setPtlYearRate(Double ptlYearRate) {
      this.ptlYearRate = ptlYearRate;
   }

   public Double getPtlArea() {
      return ptlArea;
   }

   public void setPtlArea(Double ptlArea) {
      this.ptlArea = ptlArea;
   }

   public Long getPtlPeriod() {
      return ptlPeriod;
   }

   public void setPtlPeriod(Long ptlPeriod) {
      this.ptlPeriod = ptlPeriod;
   }

   public String getPtlDescription() {
      return ptlDescription;
   }

   public void setPtlDescription(String ptlDescription) {
      this.ptlDescription = ptlDescription;
   }

   public Long getPtlMethodics() {
      return ptlMethodics;
   }

   public void setPtlMethodics(Long ptlMethodics) {
      this.ptlMethodics = ptlMethodics;
   }

   public Date getPtlCreateDate() {
      return ptlCreateDate;
   }

   public void setPtlCreateDate(Date ptlCreateDate) {
      this.ptlCreateDate = ptlCreateDate;
   }

   public Long getObjObjectId() {
      return objObjectId;
   }

   public void setObjObjectId(Long objObjectId) {
      this.objObjectId = objObjectId;
   }

}