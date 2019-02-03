package ru.devag.kamc.model;

import javax.persistence.*;

@Entity(name="i3_sbj_bst")
public class I3SbjBst {

   @Column(name = "i3sbb_sbj_bst_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3sbj_subject_id", nullable = false)
   private Long sbjSubjectId;

   @Column(name = "i3bst_basement_id", nullable = false)
   private Long bstBasementId;

   @Column(name = "i3sbb_type", nullable = false)
   private Long sbbType;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getSbjSubjectId() {
      return sbjSubjectId;
   }

   public void setSbjSubjectId(Long sbjSubjectId) {
      this.sbjSubjectId = sbjSubjectId;
   }

   public Long getBstBasementId() {
      return bstBasementId;
   }

   public void setBstBasementId(Long bstBasementId) {
      this.bstBasementId = bstBasementId;
   }

   public Long getSbbType() {
      return sbbType;
   }

   public void setSbbType(Long sbbType) {
      this.sbbType = sbbType;
   }

}