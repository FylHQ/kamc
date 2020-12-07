package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="i3_cert_component")
public class I3CertComponent {

   @Column(name = "i3cer_cert_component_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3bst_basement_id", nullable = false)
   private Long bstBasementId;

   @Column(name = "i3cer_ogrn")
   private String cerOgrn;

   @Column(name = "i3cer_date")
   private Date cerDate;

   @Column(name = "i3cer_registrator")
   private String cerRegistrator;

   @Column(name = "i3cer_series")
   private String cerSeries;

   @Column(name = "i3cer_number")
   private String cerNumber;

   @Column(name = "i3cfv_classifier_value_id", nullable = false)
   private Long cfvClassifierValueId;

   @Column(name = "i3cer_registrator_id")
   private Long cerRegistratorId;

   @Column(name = "i3cer_number_ex")
   private String cerNumberEx;

   @Column(name = "i3cer_reg_date")
   private Date cerRegDate;


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

   public String getCerOgrn() {
      return cerOgrn;
   }

   public void setCerOgrn(String cerOgrn) {
      this.cerOgrn = cerOgrn;
   }

   public Date getCerDate() {
      return cerDate;
   }

   public void setCerDate(Date cerDate) {
      this.cerDate = cerDate;
   }

   public String getCerRegistrator() {
      return cerRegistrator;
   }

   public void setCerRegistrator(String cerRegistrator) {
      this.cerRegistrator = cerRegistrator;
   }

   public String getCerSeries() {
      return cerSeries;
   }

   public void setCerSeries(String cerSeries) {
      this.cerSeries = cerSeries;
   }

   public String getCerNumber() {
      return cerNumber;
   }

   public void setCerNumber(String cerNumber) {
      this.cerNumber = cerNumber;
   }

   public Long getCfvClassifierValueId() {
      return cfvClassifierValueId;
   }

   public void setCfvClassifierValueId(Long cfvClassifierValueId) {
      this.cfvClassifierValueId = cfvClassifierValueId;
   }

   public Long getCerRegistratorId() {
      return cerRegistratorId;
   }

   public void setCerRegistratorId(Long cerRegistratorId) {
      this.cerRegistratorId = cerRegistratorId;
   }

   public String getCerNumberEx() {
      return cerNumberEx;
   }

   public void setCerNumberEx(String cerNumberEx) {
      this.cerNumberEx = cerNumberEx;
   }

   public Date getCerRegDate() {
      return cerRegDate;
   }

   public void setCerRegDate(Date cerRegDate) {
      this.cerRegDate = cerRegDate;
   }

}