package ru.devag.kamc.model;

import javax.persistence.*;

@Entity
@Table(name="i3_nsto_koef")
public class I3NstoKoef {

   @Column(name = "i3kof_koef_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3ptl_protocol_id", nullable = false)
   private Long ptlProtocolId;

   @Column(name = "i3coc_count_coeff_id")
   private Long cocCountCoeffId;

   @Column(name = "i3kof_code", nullable = false)
   private String kofCode;

   @Column(name = "i3kof_value", nullable = false)
   private Double kofValue;

   @Column(name = "i3kof_use")
   private String kofUse;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getPtlProtocolId() {
      return ptlProtocolId;
   }

   public void setPtlProtocolId(Long ptlProtocolId) {
      this.ptlProtocolId = ptlProtocolId;
   }

   public Long getCocCountCoeffId() {
      return cocCountCoeffId;
   }

   public void setCocCountCoeffId(Long cocCountCoeffId) {
      this.cocCountCoeffId = cocCountCoeffId;
   }

   public String getKofCode() {
      return kofCode;
   }

   public void setKofCode(String kofCode) {
      this.kofCode = kofCode;
   }

   public Double getKofValue() {
      return kofValue;
   }

   public void setKofValue(Double kofValue) {
      this.kofValue = kofValue;
   }

   public String getKofUse() {
      return kofUse;
   }

   public void setKofUse(String kofUse) {
      this.kofUse = kofUse;
   }

}