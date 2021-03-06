package ru.devag.kamc.model;

import javax.persistence.*;

@Entity
@Table(name="i3_kfxa_component")
public class I3KfxaComponent {

   @Column(name = "i3kfa_kfxa_component_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3cfv_classifier_value_id")
   private Long cfvClassifierValueId;

   @Column(name = "i3kfa_invnumber")
   private String kfaInvnumber;

   @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
   @JoinColumn(name = "i3obj_object_id", nullable = false)
   private I3Object object;

   @Column(name = "i3kfa_place")
   private String kfaPlace;

   @Column(name = "i3kfa_model")
   private String kfaModel;

   @Column(name = "i3kfa_is_high_cost", nullable = false)
   private String kfaIsHighCost;

   @Column(name = "i3kfa_high_bst_id")
   private Double kfaHighBstId;

   @Column(name = "i3kfa_description")
   private String kfaDescription;


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

   public String getKfaInvnumber() {
      return kfaInvnumber;
   }

   public void setKfaInvnumber(String kfaInvnumber) {
      this.kfaInvnumber = kfaInvnumber;
   }

   public String getKfaPlace() {
      return kfaPlace;
   }

   public void setKfaPlace(String kfaPlace) {
      this.kfaPlace = kfaPlace;
   }

   public String getKfaModel() {
      return kfaModel;
   }

   public void setKfaModel(String kfaModel) {
      this.kfaModel = kfaModel;
   }

   public String getKfaIsHighCost() {
      return kfaIsHighCost;
   }

   public void setKfaIsHighCost(String kfaIsHighCost) {
      this.kfaIsHighCost = kfaIsHighCost;
   }

   public Double getKfaHighBstId() {
      return kfaHighBstId;
   }

   public void setKfaHighBstId(Double kfaHighBstId) {
      this.kfaHighBstId = kfaHighBstId;
   }

   public String getKfaDescription() {
      return kfaDescription;
   }

   public void setKfaDescription(String kfaDescription) {
      this.kfaDescription = kfaDescription;
   }

   public I3Object getObject() {
      return object;
   }

   public void setObject(I3Object object) {
      this.object = object;
   }
}