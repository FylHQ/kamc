package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="i3_nsto_component")
public class I3NstoComponent {

   @Column(name = "i3nso_nsto_component_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3bst_basement_id", nullable = false)
   private Long bstBasementId;

   @Column(name = "i3nso_cfv_type_id", nullable = false)
   private Long nsoCfvTypeId;

   @Column(name = "i3nso_number", nullable = false)
   private String nsoNumber;

   @Column(name = "i3nso_start_date")
   private Date nsoStartDate;

   @Column(name = "i3nso_end_date")
   private Date nsoEndDate;

   @Column(name = "i3nso_confirm_date", nullable = false)
   private Date nsoConfirmDate;

   @Column(name = "i3nso_term_date")
   private Date nsoTermDate;


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

   public Long getNsoCfvTypeId() {
      return nsoCfvTypeId;
   }

   public void setNsoCfvTypeId(Long nsoCfvTypeId) {
      this.nsoCfvTypeId = nsoCfvTypeId;
   }

   public String getNsoNumber() {
      return nsoNumber;
   }

   public void setNsoNumber(String nsoNumber) {
      this.nsoNumber = nsoNumber;
   }

   public Date getNsoStartDate() {
      return nsoStartDate;
   }

   public void setNsoStartDate(Date nsoStartDate) {
      this.nsoStartDate = nsoStartDate;
   }

   public Date getNsoEndDate() {
      return nsoEndDate;
   }

   public void setNsoEndDate(Date nsoEndDate) {
      this.nsoEndDate = nsoEndDate;
   }

   public Date getNsoConfirmDate() {
      return nsoConfirmDate;
   }

   public void setNsoConfirmDate(Date nsoConfirmDate) {
      this.nsoConfirmDate = nsoConfirmDate;
   }

   public Date getNsoTermDate() {
      return nsoTermDate;
   }

   public void setNsoTermDate(Date nsoTermDate) {
      this.nsoTermDate = nsoTermDate;
   }

}