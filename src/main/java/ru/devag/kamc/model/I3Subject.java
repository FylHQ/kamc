package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;

@Entity(name="i3_subject")
public class I3Subject {

   @Column(name = "i3sbj_subject_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3cat_category_id", nullable = false)
   private Long catCategoryId;

   @Column(name = "i3sbj_number", nullable = false)
   private String sbjNumber;

   @Column(name = "i3sts_status_id")
   private Long stsStatusId;

   @Column(name = "i3sbj_description")
   private String sbjDescription;

   @Column(name = "i3sbj_date")
   @CreationTimestamp
   private Date sbjDate;

   @Column(name = "i3sbj_user")
   @Transient
   private String sbjUser;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getCatCategoryId() {
      return catCategoryId;
   }

   public void setCatCategoryId(Long catCategoryId) {
      this.catCategoryId = catCategoryId;
   }

   public String getSbjNumber() {
      return sbjNumber;
   }

   public void setSbjNumber(String sbjNumber) {
      this.sbjNumber = sbjNumber;
   }

   public Long getStsStatusId() {
      return stsStatusId;
   }

   public void setStsStatusId(Long stsStatusId) {
      this.stsStatusId = stsStatusId;
   }

   public String getSbjDescription() {
      return sbjDescription;
   }

   public void setSbjDescription(String sbjDescription) {
      this.sbjDescription = sbjDescription;
   }

   public Date getSbjDate() {
      return sbjDate;
   }

   public void setSbjDate(Date sbjDate) {
      this.sbjDate = sbjDate;
   }

   public String getSbjUser() {
      return sbjUser;
   }

   public void setSbjUser(String sbjUser) {
      this.sbjUser = sbjUser;
   }

}