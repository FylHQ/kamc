package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name="i3_cntr_component")
public class I3CntrComponent {

   @Column(name = "i3cnr_cntr_component_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long cnrCntrComponentId;

   @Column(name = "i3bst_basement_id", nullable = false)
   private Long bstBasementId;

   @Column(name = "i3cnr_number", nullable = false)
   private String cnrNumber;

   @Column(name = "i3cnr_start_date")
   private Date cnrStartDate;

   @Column(name = "i3cnr_end_date")
   private Date cnrEndDate;

   @Column(name = "i3cnr_confirm_date")
   private Date cnrConfirmDate;

   @Column(name = "i3cnr_common_value")
   private Double cnrCommonValue;

   @Column(name = "i3cfv_classifier_value_id", nullable = false)
   private Long cfvClassifierValueId;

   @Column(name = "i3cnr_description")
   private String cnrDescription;

   @Column(name = "i3cnr_payment_period", nullable = false)
   private Long cnrPaymentPeriod;

   @Column(name = "i3cnr_pay_start_date")
   private Date cnrPayStartDate;

   @Column(name = "i3cnr_annotation")
   private String cnrAnnotation;

   @Column(name = "i3cnr_usepercrate", nullable = false)
   private String cnrUsepercrate;

   @Column(name = "i3cnr_feerate", nullable = false)
   private Double cnrFeerate;

   @Column(name = "i3cnr_annotation_clf_id")
   private Long cnrAnnotationClfId;

   @Column(name = "i3cnr_object_condition_id")
   private Long cnrObjectConditionId;

   @Column(name = "i3cnr_register_clf_id")
   private Long cnrRegisterClfId;

   @Column(name = "i3cnr_register_date")
   private Date cnrRegisterDate;

   @Column(name = "i3cnr_is_sublease", nullable = false)
   private String cnrIsSublease;

   @Column(name = "i3cnr_sublease_description")
   private String cnrSubleaseDescription;

   @Column(name = "i3cnr_register_number")
   private String cnrRegisterNumber;

   @Column(name = "i3cnr_origin_clf_id")
   private Long cnrOriginClfId;

   @Column(name = "i3cnr_ancestor_bst_id")
   private Long cnrAncestorBstId;

   @Column(name = "i3cnr_bldn_state_clf_id")
   private Long cnrBldnStateClfId;

   @Column(name = "i3cnr_release_reason")
   private String cnrReleaseReason;

   @Column(name = "i3cnr_release_date")
   private Date cnrReleaseDate;

   @Column(name = "i3cnr_is_switch_rights", nullable = false)
   private String cnrIsSwitchRights;

   @Column(name = "i3cnr_sign_date")
   private Date cnrSignDate;


   public Long getCnrCntrComponentId() {
      return cnrCntrComponentId;
   }

   public void setCnrCntrComponentId(Long cnrCntrComponentId) {
      this.cnrCntrComponentId = cnrCntrComponentId;
   }

   public Long getBstBasementId() {
      return bstBasementId;
   }

   public void setBstBasementId(Long bstBasementId) {
      this.bstBasementId = bstBasementId;
   }

   public String getCnrNumber() {
      return cnrNumber;
   }

   public void setCnrNumber(String cnrNumber) {
      this.cnrNumber = cnrNumber;
   }

   public Date getCnrStartDate() {
      return cnrStartDate;
   }

   public void setCnrStartDate(Date cnrStartDate) {
      this.cnrStartDate = cnrStartDate;
   }

   public Date getCnrEndDate() {
      return cnrEndDate;
   }

   public void setCnrEndDate(Date cnrEndDate) {
      this.cnrEndDate = cnrEndDate;
   }

   public Date getCnrConfirmDate() {
      return cnrConfirmDate;
   }

   public void setCnrConfirmDate(Date cnrConfirmDate) {
      this.cnrConfirmDate = cnrConfirmDate;
   }

   public Double getCnrCommonValue() {
      return cnrCommonValue;
   }

   public void setCnrCommonValue(Double cnrCommonValue) {
      this.cnrCommonValue = cnrCommonValue;
   }

   public Long getCfvClassifierValueId() {
      return cfvClassifierValueId;
   }

   public void setCfvClassifierValueId(Long cfvClassifierValueId) {
      this.cfvClassifierValueId = cfvClassifierValueId;
   }

   public String getCnrDescription() {
      return cnrDescription;
   }

   public void setCnrDescription(String cnrDescription) {
      this.cnrDescription = cnrDescription;
   }

   public Long getCnrPaymentPeriod() {
      return cnrPaymentPeriod;
   }

   public void setCnrPaymentPeriod(Long cnrPaymentPeriod) {
      this.cnrPaymentPeriod = cnrPaymentPeriod;
   }

   public Date getCnrPayStartDate() {
      return cnrPayStartDate;
   }

   public void setCnrPayStartDate(Date cnrPayStartDate) {
      this.cnrPayStartDate = cnrPayStartDate;
   }

   public String getCnrAnnotation() {
      return cnrAnnotation;
   }

   public void setCnrAnnotation(String cnrAnnotation) {
      this.cnrAnnotation = cnrAnnotation;
   }

   public String getCnrUsepercrate() {
      return cnrUsepercrate;
   }

   public void setCnrUsepercrate(String cnrUsepercrate) {
      this.cnrUsepercrate = cnrUsepercrate;
   }

   public Double getCnrFeerate() {
      return cnrFeerate;
   }

   public void setCnrFeerate(Double cnrFeerate) {
      this.cnrFeerate = cnrFeerate;
   }

   public Long getCnrAnnotationClfId() {
      return cnrAnnotationClfId;
   }

   public void setCnrAnnotationClfId(Long cnrAnnotationClfId) {
      this.cnrAnnotationClfId = cnrAnnotationClfId;
   }

   public Long getCnrObjectConditionId() {
      return cnrObjectConditionId;
   }

   public void setCnrObjectConditionId(Long cnrObjectConditionId) {
      this.cnrObjectConditionId = cnrObjectConditionId;
   }

   public Long getCnrRegisterClfId() {
      return cnrRegisterClfId;
   }

   public void setCnrRegisterClfId(Long cnrRegisterClfId) {
      this.cnrRegisterClfId = cnrRegisterClfId;
   }

   public Date getCnrRegisterDate() {
      return cnrRegisterDate;
   }

   public void setCnrRegisterDate(Date cnrRegisterDate) {
      this.cnrRegisterDate = cnrRegisterDate;
   }

   public String getCnrIsSublease() {
      return cnrIsSublease;
   }

   public void setCnrIsSublease(String cnrIsSublease) {
      this.cnrIsSublease = cnrIsSublease;
   }

   public String getCnrSubleaseDescription() {
      return cnrSubleaseDescription;
   }

   public void setCnrSubleaseDescription(String cnrSubleaseDescription) {
      this.cnrSubleaseDescription = cnrSubleaseDescription;
   }

   public String getCnrRegisterNumber() {
      return cnrRegisterNumber;
   }

   public void setCnrRegisterNumber(String cnrRegisterNumber) {
      this.cnrRegisterNumber = cnrRegisterNumber;
   }

   public Long getCnrOriginClfId() {
      return cnrOriginClfId;
   }

   public void setCnrOriginClfId(Long cnrOriginClfId) {
      this.cnrOriginClfId = cnrOriginClfId;
   }

   public Long getCnrAncestorBstId() {
      return cnrAncestorBstId;
   }

   public void setCnrAncestorBstId(Long cnrAncestorBstId) {
      this.cnrAncestorBstId = cnrAncestorBstId;
   }

   public Long getCnrBldnStateClfId() {
      return cnrBldnStateClfId;
   }

   public void setCnrBldnStateClfId(Long cnrBldnStateClfId) {
      this.cnrBldnStateClfId = cnrBldnStateClfId;
   }

   public String getCnrReleaseReason() {
      return cnrReleaseReason;
   }

   public void setCnrReleaseReason(String cnrReleaseReason) {
      this.cnrReleaseReason = cnrReleaseReason;
   }

   public Date getCnrReleaseDate() {
      return cnrReleaseDate;
   }

   public void setCnrReleaseDate(Date cnrReleaseDate) {
      this.cnrReleaseDate = cnrReleaseDate;
   }

   public String getCnrIsSwitchRights() {
      return cnrIsSwitchRights;
   }

   public void setCnrIsSwitchRights(String cnrIsSwitchRights) {
      this.cnrIsSwitchRights = cnrIsSwitchRights;
   }

   public Date getCnrSignDate() {
      return cnrSignDate;
   }

   public void setCnrSignDate(Date cnrSignDate) {
      this.cnrSignDate = cnrSignDate;
   }

}