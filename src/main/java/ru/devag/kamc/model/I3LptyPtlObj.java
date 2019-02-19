package ru.devag.kamc.model;

import javax.persistence.*;

@Entity
@Table(name="i3_lpty_ptl_obj")
public class I3LptyPtlObj {

   @Column(name = "i3pto_ptl_obj_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
   @JoinColumn(name = "i3ptl_protocol_id", nullable = false)
   private I3LptyProtocol proto;

   @Column(name = "i3obj_object_id", nullable = false)
   private Long objObjectId;

   @Column(name = "i3pto_period_rate")
   private Double ptoPeriodRate;

   @Column(name = "i3pto_year_rate")
   private Double ptoYearRate;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public I3LptyProtocol getProto() {
      return proto;
   }

   public void setProto(I3LptyProtocol proto) {
      this.proto = proto;
   }

   public Long getObjObjectId() {
      return objObjectId;
   }

   public void setObjObjectId(Long objObjectId) {
      this.objObjectId = objObjectId;
   }

   public Double getPtoPeriodRate() {
      return ptoPeriodRate;
   }

   public void setPtoPeriodRate(Double ptoPeriodRate) {
      this.ptoPeriodRate = ptoPeriodRate;
   }

   public Double getPtoYearRate() {
      return ptoYearRate;
   }

   public void setPtoYearRate(Double ptoYearRate) {
      this.ptoYearRate = ptoYearRate;
   }

}