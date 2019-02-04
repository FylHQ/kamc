package ru.devag.kamc.model;

import javax.persistence.*;

@Entity
@Table(name="i3_sbj_rtn")
public class I3SbjRtn {

   @Column(name = "i3sbr_sbj_rtn_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3rtn_relation_id", nullable = false)
   private Long rtnRelationId;

   @Column(name = "i3sbj_subject_id", nullable = false)
   private Long sbjSubjectId;

   @Column(name = "i3tst_test_id")
   private Long tstTestId;

   @Column(name = "i3sbr_type", nullable = false)
   private Long sbrType;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getRtnRelationId() {
      return rtnRelationId;
   }

   public void setRtnRelationId(Long rtnRelationId) {
      this.rtnRelationId = rtnRelationId;
   }

   public Long getSbjSubjectId() {
      return sbjSubjectId;
   }

   public void setSbjSubjectId(Long sbjSubjectId) {
      this.sbjSubjectId = sbjSubjectId;
   }

   public Long getTstTestId() {
      return tstTestId;
   }

   public void setTstTestId(Long tstTestId) {
      this.tstTestId = tstTestId;
   }

   public Long getSbrType() {
      return sbrType;
   }

   public void setSbrType(Long sbrType) {
      this.sbrType = sbrType;
   }

}