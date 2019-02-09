package ru.devag.kamc.model;

import javax.persistence.*;

@Entity
@Table(name = "i3_land_component")
public class I3LandComponent {

   @Column(name = "i3lnd_land_component_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3cfv_classifier_value_id")
   private Long cfvClassifierValueId;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "i3obj_object_id", nullable = false)
   private I3Object object;

   @Column(name = "i3lnd_mesuare_area")
   private Double lndMesuareArea;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getCfvClassifierValueId() {
      return cfvClassifierValueId;
   }

   public void setCfvClassifierValueId(Long cfvClassifierValueId) {
      this.cfvClassifierValueId = cfvClassifierValueId;
   }

   public Double getLndMesuareArea() {
      return lndMesuareArea;
   }

   public void setLndMesuareArea(Double lndMesuareArea) {
      this.lndMesuareArea = lndMesuareArea;
   }

   public I3Object getObject() {
      return object;
   }

   public void setObject(I3Object object) {
      this.object = object;
   }
}