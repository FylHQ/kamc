package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="i3_trat_property")
public class I3TratProperty {

   @Column(name = "i3trp_trat_property_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
   @JoinColumn(name = "i3trt_trat_component_id", nullable = false)
   private I3TratComponent trat;

   @Column(name = "i3trp_balance_cost")
   private Double trpBalanceCost;

   @Column(name = "i3trp_remain_cost")
   private Double trpRemainCost;

   @Column(name = "i3trp_deterioration")
   private Double trpDeterioration;

   @Column(name = "i3trp_leasing_rate")
   private Double trpLeasingRate;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public I3TratComponent getTrat() {
      return trat;
   }

   public void setTrat(I3TratComponent trat) {
      this.trat = trat;
   }

   public Double getTrpBalanceCost() {
      return trpBalanceCost;
   }

   public void setTrpBalanceCost(Double trpBalanceCost) {
      this.trpBalanceCost = trpBalanceCost;
   }

   public Double getTrpRemainCost() {
      return trpRemainCost;
   }

   public void setTrpRemainCost(Double trpRemainCost) {
      this.trpRemainCost = trpRemainCost;
   }

   public Double getTrpDeterioration() {
      return trpDeterioration;
   }

   public void setTrpDeterioration(Double trpDeterioration) {
      this.trpDeterioration = trpDeterioration;
   }

   public Double getTrpLeasingRate() {
      return trpLeasingRate;
   }

   public void setTrpLeasingRate(Double trpLeasingRate) {
      this.trpLeasingRate = trpLeasingRate;
   }

}