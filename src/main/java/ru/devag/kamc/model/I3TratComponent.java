package ru.devag.kamc.model;

import javax.persistence.*;

@Entity
@Table(name="i3_trat_component")
public class I3TratComponent {

   @Column(name = "i3trt_trat_component_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
   @JoinColumn(name = "i3rtn_relation_id", nullable = false)
   private I3Relation relation;

   @Column(name = "i3cfv_classifier_value_id", nullable = false)
   private Long cfvClassifierValueId;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public I3Relation getRelation() {
      return relation;
   }

   public void setRelation(I3Relation relation) {
      this.relation = relation;
   }

   public Long getCfvClassifierValueId() {
      return cfvClassifierValueId;
   }

   public void setCfvClassifierValueId(Long cfvClassifierValueId) {
      this.cfvClassifierValueId = cfvClassifierValueId;
   }

}