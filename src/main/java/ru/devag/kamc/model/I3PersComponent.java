package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name="i3_pers_component")
public class I3PersComponent {

   @Column(name = "i3prs_prs_component_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3sbj_subject_id", nullable = false)
   private Long sbjSubjectId;

   @Column(name = "i3prs_first_name")
   private String prsFirstName;

   @Column(name = "i3prs_second_name")
   private String prsSecondName;

   @Column(name = "i3prs_last_name")
   private String prsLastName;

   @Column(name = "i3prs_birthday")
   private Date prsBirthday;

   @Column(name = "i3prs_sex")
   private String prsSex;

   @Column(name = "i3prs_itn")
   private String prsItn;

   @Column(name = "i3prs_istaxfree", nullable = false)
   private String prsIstaxfree;

   @Column(name = "i3prs_snils")
   private Double prsSnils;


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

   public String getPrsFirstName() {
      return prsFirstName;
   }

   public void setPrsFirstName(String prsFirstName) {
      this.prsFirstName = prsFirstName;
   }

   public String getPrsSecondName() {
      return prsSecondName;
   }

   public void setPrsSecondName(String prsSecondName) {
      this.prsSecondName = prsSecondName;
   }

   public String getPrsLastName() {
      return prsLastName;
   }

   public void setPrsLastName(String prsLastName) {
      this.prsLastName = prsLastName;
   }

   public Date getPrsBirthday() {
      return prsBirthday;
   }

   public void setPrsBirthday(Date prsBirthday) {
      this.prsBirthday = prsBirthday;
   }

   public String getPrsSex() {
      return prsSex;
   }

   public void setPrsSex(String prsSex) {
      this.prsSex = prsSex;
   }

   public String getPrsItn() {
      return prsItn;
   }

   public void setPrsItn(String prsItn) {
      this.prsItn = prsItn;
   }

   public String getPrsIstaxfree() {
      return prsIstaxfree;
   }

   public void setPrsIstaxfree(String prsIstaxfree) {
      this.prsIstaxfree = prsIstaxfree;
   }

   public Double getPrsSnils() {
      return prsSnils;
   }

   public void setPrsSnils(Double prsSnils) {
      this.prsSnils = prsSnils;
   }

}