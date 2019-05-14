package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="i3_nobj_component")
public class I3NobjComponent {

   @Column(name = "i3nob_nobj_component_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "i3obj_object_id", nullable = false)
   private I3Object object;

   @Column(name = "i3nob_actual_number")
   private String nobActualNumber;

   @Column(name = "i3nob_initial_number")
   private String nobInitialNumber;

   @Column(name = "i3nob_cfv_type_id", nullable = false)
   private Long nobCfvTypeId;

   @Column(name = "i3nob_cfv_spec_id")
   private Long nobCfvSpecId;

   @Column(name = "i3nob_avg_unit_cost")
   private Double nobAvgUnitCost;

   @Column(name = "i3nob_cad_block")
   private Long nobCadBlock;

   @Column(name = "i3nob_area")
   private Double nobArea;

   @Column(name = "i3nob_placement")
   private String nobPlacement;

   @Column(name = "i3nob_set_date")
   private Date nobSetDate;

   @Column(name = "i3nob_prcl_area")
   private Double nobPrclArea;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public I3Object getObject() {
      return object;
   }

   public void setObject(I3Object object) {
      this.object = object;
   }

   public String getNobActualNumber() {
      return nobActualNumber;
   }

   public void setNobActualNumber(String nobActualNumber) {
      this.nobActualNumber = nobActualNumber;
   }

   public String getNobInitialNumber() {
      return nobInitialNumber;
   }

   public void setNobInitialNumber(String nobInitialNumber) {
      this.nobInitialNumber = nobInitialNumber;
   }

   public Long getNobCfvTypeId() {
      return nobCfvTypeId;
   }

   public void setNobCfvTypeId(Long nobCfvTypeId) {
      this.nobCfvTypeId = nobCfvTypeId;
   }

   public Long getNobCfvSpecId() {
      return nobCfvSpecId;
   }

   public void setNobCfvSpecId(Long nobCfvSpecId) {
      this.nobCfvSpecId = nobCfvSpecId;
   }

   public Double getNobAvgUnitCost() {
      return nobAvgUnitCost;
   }

   public void setNobAvgUnitCost(Double nobAvgUnitCost) {
      this.nobAvgUnitCost = nobAvgUnitCost;
   }

   public Long getNobCadBlock() {
      return nobCadBlock;
   }

   public void setNobCadBlock(Long nobCadBlock) {
      this.nobCadBlock = nobCadBlock;
   }

   public Double getNobArea() {
      return nobArea;
   }

   public void setNobArea(Double nobArea) {
      this.nobArea = nobArea;
   }

   public String getNobPlacement() {
      return nobPlacement;
   }

   public void setNobPlacement(String nobPlacement) {
      this.nobPlacement = nobPlacement;
   }

   public Date getNobSetDate() {
      return nobSetDate;
   }

   public void setNobSetDate(Date nobSetDate) {
      this.nobSetDate = nobSetDate;
   }

   public Double getNobPrclArea() {
      return nobPrclArea;
   }

   public void setNobPrclArea(Double nobPrclArea) {
      this.nobPrclArea = nobPrclArea;
   }

}