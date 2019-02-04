package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name="i3_lpty_component")
public class I3LptyComponent {

   @Column(name = "i3lpy_lpty_component_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3bst_basement_id", nullable = false)
   private Long bstBasementId;

   @Column(name = "i3cfv_classifier_value_id", nullable = false)
   private Long cfvClassifierValueId;

   @Column(name = "i3lpy_number")
   private String lpyNumber;

   @Column(name = "i3lpy_start_date")
   private Date lpyStartDate;

   @Column(name = "i3lpy_end_date")
   private Date lpyEndDate;

   @Column(name = "i3lpy_confirm_date")
   private Date lpyConfirmDate;

   @Column(name = "i3lpy_register_date")
   private Date lpyRegisterDate;

   @Column(name = "i3lpy_description")
   private String lpyDescription;

   @Column(name = "i3lpy_payment_period")
   private Double lpyPaymentPeriod;

   @Column(name = "i3lpy_pay_start_date")
   private Date lpyPayStartDate;

   @Column(name = "i3lpy_usepercrate")
   private String lpyUsepercrate;

   @Column(name = "i3lpy_feerate")
   private Double lpyFeerate;

   @Column(name = "i3lpy_register_clf_id")
   private Long lpyRegisterClfId;

   @Column(name = "i3lpy_is_other_rate", nullable = false)
   private String lpyIsOtherRate;

   @Column(name = "i3lpy_lea_rate")
   private Long lpyLeaRate;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getBstBasementId() {
      return bstBasementId;
   }

   public void setBstBasementId(Long bstBasementId) {
      this.bstBasementId = bstBasementId;
   }

   public Long getCfvClassifierValueId() {
      return cfvClassifierValueId;
   }

   public void setCfvClassifierValueId(Long cfvClassifierValueId) {
      this.cfvClassifierValueId = cfvClassifierValueId;
   }

   public String getLpyNumber() {
      return lpyNumber;
   }

   public void setLpyNumber(String lpyNumber) {
      this.lpyNumber = lpyNumber;
   }

   public Date getLpyStartDate() {
      return lpyStartDate;
   }

   public void setLpyStartDate(Date lpyStartDate) {
      this.lpyStartDate = lpyStartDate;
   }

   public Date getLpyEndDate() {
      return lpyEndDate;
   }

   public void setLpyEndDate(Date lpyEndDate) {
      this.lpyEndDate = lpyEndDate;
   }

   public Date getLpyConfirmDate() {
      return lpyConfirmDate;
   }

   public void setLpyConfirmDate(Date lpyConfirmDate) {
      this.lpyConfirmDate = lpyConfirmDate;
   }

   public Date getLpyRegisterDate() {
      return lpyRegisterDate;
   }

   public void setLpyRegisterDate(Date lpyRegisterDate) {
      this.lpyRegisterDate = lpyRegisterDate;
   }

   public String getLpyDescription() {
      return lpyDescription;
   }

   public void setLpyDescription(String lpyDescription) {
      this.lpyDescription = lpyDescription;
   }

   public Double getLpyPaymentPeriod() {
      return lpyPaymentPeriod;
   }

   public void setLpyPaymentPeriod(Double lpyPaymentPeriod) {
      this.lpyPaymentPeriod = lpyPaymentPeriod;
   }

   public Date getLpyPayStartDate() {
      return lpyPayStartDate;
   }

   public void setLpyPayStartDate(Date lpyPayStartDate) {
      this.lpyPayStartDate = lpyPayStartDate;
   }

   public String getLpyUsepercrate() {
      return lpyUsepercrate;
   }

   public void setLpyUsepercrate(String lpyUsepercrate) {
      this.lpyUsepercrate = lpyUsepercrate;
   }

   public Double getLpyFeerate() {
      return lpyFeerate;
   }

   public void setLpyFeerate(Double lpyFeerate) {
      this.lpyFeerate = lpyFeerate;
   }

   public Long getLpyRegisterClfId() {
      return lpyRegisterClfId;
   }

   public void setLpyRegisterClfId(Long lpyRegisterClfId) {
      this.lpyRegisterClfId = lpyRegisterClfId;
   }

   public String getLpyIsOtherRate() {
      return lpyIsOtherRate;
   }

   public void setLpyIsOtherRate(String lpyIsOtherRate) {
      this.lpyIsOtherRate = lpyIsOtherRate;
   }

   public Long getLpyLeaRate() {
      return lpyLeaRate;
   }

   public void setLpyLeaRate(Long lpyLeaRate) {
      this.lpyLeaRate = lpyLeaRate;
   }
}