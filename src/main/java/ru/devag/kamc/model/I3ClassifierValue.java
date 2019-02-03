package ru.devag.kamc.model;

import javax.persistence.*;

@Entity(name="i3_classifier_value")
public class I3ClassifierValue {

   @Column(name = "i3cfv_classifier_value_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3cfv_value", nullable = false)
   private String cfvValue;

   @Column(name = "i3cfv_code", nullable = false)
   private String cfvCode;

   @Column(name = "i3cfv_parent_id")
   private Long cfvParentId;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getCfvValue() {
      return cfvValue;
   }

   public void setCfvValue(String cfvValue) {
      this.cfvValue = cfvValue;
   }

   public String getCfvCode() {
      return cfvCode;
   }

   public void setCfvCode(String cfvCode) {
      this.cfvCode = cfvCode;
   }

   public Long getCfvParentId() {
      return cfvParentId;
   }

   public void setCfvParentId(Long cfvParentId) {
      this.cfvParentId = cfvParentId;
   }

}