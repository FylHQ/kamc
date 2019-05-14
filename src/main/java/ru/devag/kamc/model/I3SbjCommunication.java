package ru.devag.kamc.model;

import javax.persistence.*;

@Entity
@Table(name="i3_sbj_communication")
public class I3SbjCommunication {

   @Column(name = "i3sbc_sbj_communication_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3sbj_subject_id", nullable = false)
   private Long sbjSubjectId;

   @Column(name = "i3sbc_type_code", nullable = false)
   private String sbcTypeCode;

   @Column(name = "i3sbc_value", nullable = false)
   private String sbcValue;


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

   public String getSbcTypeCode() {
      return sbcTypeCode;
   }

   public void setSbcTypeCode(String sbcTypeCode) {
      this.sbcTypeCode = sbcTypeCode;
   }

   public String getSbcValue() {
      return sbcValue;
   }

   public void setSbcValue(String sbcValue) {
      this.sbcValue = sbcValue;
   }

}