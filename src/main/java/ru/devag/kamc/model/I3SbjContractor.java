package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="i3_sbj_contractor")
public class I3SbjContractor {

   @Column(name = "i3sbc_sbj_contractor_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3sbb_sbj_bst_id", nullable = false)
   private Long sbbSbjBstId;

   @Column(name = "i3sbc_area")
   private Double sbcArea;

   @Column(name = "i3sbc_area_part_numerator")
   private Long sbcAreaPartNumerator;

   @Column(name = "i3sbc_area_part_denominator")
   private Long sbcAreaPartDenominator;

   @Column(name = "i3sbc_bus_is_pers", nullable = false)
   private String sbcBusIsPers;

   @Column(name = "i3sbc_is_free", nullable = false)
   private String sbcIsFree;

   @Column(name = "i3sbc_okved_id")
   private Long sbcOkvedId;

   @Column(name = "i3sbc_okved")
   private String sbcOkved;

   @Column(name = "i3sbc_description")
   private String sbcDescription;

   @Column(name = "i3sbc_small_business_type")
   private String sbcSmallBusinessType;

   @Column(name = "i3sbc_small_business_reg_date")
   private Date sbcSmallBusinessRegDate;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getSbbSbjBstId() {
      return sbbSbjBstId;
   }

   public void setSbbSbjBstId(Long sbbSbjBstId) {
      this.sbbSbjBstId = sbbSbjBstId;
   }

   public Double getSbcArea() {
      return sbcArea;
   }

   public void setSbcArea(Double sbcArea) {
      this.sbcArea = sbcArea;
   }

   public Long getSbcAreaPartNumerator() {
      return sbcAreaPartNumerator;
   }

   public void setSbcAreaPartNumerator(Long sbcAreaPartNumerator) {
      this.sbcAreaPartNumerator = sbcAreaPartNumerator;
   }

   public Long getSbcAreaPartDenominator() {
      return sbcAreaPartDenominator;
   }

   public void setSbcAreaPartDenominator(Long sbcAreaPartDenominator) {
      this.sbcAreaPartDenominator = sbcAreaPartDenominator;
   }

   public String getSbcBusIsPers() {
      return sbcBusIsPers;
   }

   public void setSbcBusIsPers(String sbcBusIsPers) {
      this.sbcBusIsPers = sbcBusIsPers;
   }

   public String getSbcIsFree() {
      return sbcIsFree;
   }

   public void setSbcIsFree(String sbcIsFree) {
      this.sbcIsFree = sbcIsFree;
   }

   public Long getSbcOkvedId() {
      return sbcOkvedId;
   }

   public void setSbcOkvedId(Long sbcOkvedId) {
      this.sbcOkvedId = sbcOkvedId;
   }

   public String getSbcOkved() {
      return sbcOkved;
   }

   public void setSbcOkved(String sbcOkved) {
      this.sbcOkved = sbcOkved;
   }

   public String getSbcDescription() {
      return sbcDescription;
   }

   public void setSbcDescription(String sbcDescription) {
      this.sbcDescription = sbcDescription;
   }

   public String getSbcSmallBusinessType() {
      return sbcSmallBusinessType;
   }

   public void setSbcSmallBusinessType(String sbcSmallBusinessType) {
      this.sbcSmallBusinessType = sbcSmallBusinessType;
   }

   public Date getSbcSmallBusinessRegDate() {
      return sbcSmallBusinessRegDate;
   }

   public void setSbcSmallBusinessRegDate(Date sbcSmallBusinessRegDate) {
      this.sbcSmallBusinessRegDate = sbcSmallBusinessRegDate;
   }

}