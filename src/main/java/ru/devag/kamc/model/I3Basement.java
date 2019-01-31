package ru.devag.kamc.model;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity(name="i3_basement")
public class I3Basement {

   @Column(name = "i3bst_basement_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long bstBasementId;

   @Column(name = "i3cat_category_id", nullable = false)
   private Long catCategoryId;

   @Column(name = "i3bst_number", nullable = false)
   private String bstNumber;

   @Column(name = "i3sts_status_id")
   private Long stsStatusId;

   @Column(name = "i3bst_description")
   private String bstDescription;

   @Column(name = "i3bst_date")
   @CreationTimestamp
   @Temporal(TemporalType.TIMESTAMP)
   private Date bstDate;

   @Column(name = "i3bst_user")
   @Transient
   private String bstUser;


   public Long getBstBasementId() {
      return bstBasementId;
   }

   public void setBstBasementId(Long bstBasementId) {
      this.bstBasementId = bstBasementId;
   }

   public Long getCatCategoryId() {
      return catCategoryId;
   }

   public void setCatCategoryId(Long catCategoryId) {
      this.catCategoryId = catCategoryId;
   }

   public String getBstNumber() {
      return bstNumber;
   }

   public void setBstNumber(String bstNumber) {
      this.bstNumber = bstNumber;
   }

   public Long getStsStatusId() {
      return stsStatusId;
   }

   public void setStsStatusId(Long stsStatusId) {
      this.stsStatusId = stsStatusId;
   }

   public String getBstDescription() {
      return bstDescription;
   }

   public void setBstDescription(String bstDescription) {
      this.bstDescription = bstDescription;
   }

   public Date getBstDate() {
      return bstDate;
   }

   public void setBstDate(Date bstDate) {
      this.bstDate = bstDate;
   }

   public String getBstUser() {
      return bstUser;
   }

   public void setBstUser(String bstUser) {
      this.bstUser = bstUser;
   }

}