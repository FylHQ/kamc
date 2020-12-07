package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "i3_prcl_cost")
public class I3PrclCost {

   @Column(name = "i3pco_cost_id", nullable = false)
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3prc_prcl_component_id", nullable = false)
   private Long prcPrclComponentId;

   @Column(name = "i3pco_date", nullable = false)
   private Date pcoDate;

   @Column(name = "i3pco_cad_cost")
   private Double pcoCadCost;

   @Column(name = "i3pco_base_cost")
   private Double pcoBaseCost;

   @Column(name = "i3pco_description")
   private String pcoDescription;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getPrcPrclComponentId() {
      return prcPrclComponentId;
   }

   public void setPrcPrclComponentId(Long prcPrclComponentId) {
      this.prcPrclComponentId = prcPrclComponentId;
   }

   public Date getPcoDate() {
      return pcoDate;
   }

   public void setPcoDate(Date pcoDate) {
      this.pcoDate = pcoDate;
   }

   public Double getPcoCadCost() {
      return pcoCadCost;
   }

   public void setPcoCadCost(Double pcoCadCost) {
      this.pcoCadCost = pcoCadCost;
   }

   public Double getPcoBaseCost() {
      return pcoBaseCost;
   }

   public void setPcoBaseCost(Double pcoBaseCost) {
      this.pcoBaseCost = pcoBaseCost;
   }

   public String getPcoDescription() {
      return pcoDescription;
   }

   public void setPcoDescription(String pcoDescription) {
      this.pcoDescription = pcoDescription;
   }

   @Override
   public String toString() {
      return "I3PrclCost {cost: " + pcoCadCost + ", date: " + pcoDate + "}";
   }
}