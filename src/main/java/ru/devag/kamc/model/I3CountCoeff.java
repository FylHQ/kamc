package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="i3_count_coeff")
public class I3CountCoeff {

   @Column(name = "i3coc_count_coeff_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3coc_code", nullable = false)
   private String cocCode;

   @Column(name = "i3coc_name", nullable = false)
   private String cocName;

   @Column(name = "i3coc_min", nullable = false)
   private Double cocMin;

   @Column(name = "i3coc_max", nullable = false)
   private Double cocMax;

   @Column(name = "i3coc_default", nullable = false)
   private Double cocDefault;

   @Column(name = "i3coc_use", nullable = false)
   private String cocUse;

   @Column(name = "i3coc_module")
   private String cocModule;

   @Column(name = "i3bst_basement_id")
   private Long bstBasementId;

   @Column(name = "i3coc_is_increase", nullable = false)
   private String cocIsIncrease;

   @Column(name = "i3coc_use_in_calc", nullable = false)
   private String cocUseInCalc;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getCocCode() {
      return cocCode;
   }

   public void setCocCode(String cocCode) {
      this.cocCode = cocCode;
   }

   public String getCocName() {
      return cocName;
   }

   public void setCocName(String cocName) {
      this.cocName = cocName;
   }

   public Double getCocMin() {
      return cocMin;
   }

   public void setCocMin(Double cocMin) {
      this.cocMin = cocMin;
   }

   public Double getCocMax() {
      return cocMax;
   }

   public void setCocMax(Double cocMax) {
      this.cocMax = cocMax;
   }

   public Double getCocDefault() {
      return cocDefault;
   }

   public void setCocDefault(Double cocDefault) {
      this.cocDefault = cocDefault;
   }

   public String getCocUse() {
      return cocUse;
   }

   public void setCocUse(String cocUse) {
      this.cocUse = cocUse;
   }

   public String getCocModule() {
      return cocModule;
   }

   public void setCocModule(String cocModule) {
      this.cocModule = cocModule;
   }

   public Long getBstBasementId() {
      return bstBasementId;
   }

   public void setBstBasementId(Long bstBasementId) {
      this.bstBasementId = bstBasementId;
   }

   public String getCocIsIncrease() {
      return cocIsIncrease;
   }

   public void setCocIsIncrease(String cocIsIncrease) {
      this.cocIsIncrease = cocIsIncrease;
   }

   public String getCocUseInCalc() {
      return cocUseInCalc;
   }

   public void setCocUseInCalc(String cocUseInCalc) {
      this.cocUseInCalc = cocUseInCalc;
   }

}