package ru.devag.kamc.model;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity(name="i3_relation")
public class I3Relation {

   @Column(name = "i3rtn_relation_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long rtnRelationId;

   @Column(name = "i3cat_category_id", nullable = false)
   private Long catCategoryId;

   @Column(name = "i3rtn_number", nullable = false)
   private String rtnNumber;

   @Column(name = "i3rtn_description")
   private String rtnDescription;

   @Column(name = "i3sts_status_id", nullable = false)
   private Long stsStatusId;

   @Column(name = "i3rtn_date")
   @CreationTimestamp
   @Temporal(TemporalType.TIMESTAMP)
   private Date rtnDate;

   @Column(name = "i3rtn_user")
   @Transient
   private String rtnUser;


   public Long getRtnRelationId() {
      return rtnRelationId;
   }

   public void setRtnRelationId(Long rtnRelationId) {
      this.rtnRelationId = rtnRelationId;
   }

   public Long getCatCategoryId() {
      return catCategoryId;
   }

   public void setCatCategoryId(Long catCategoryId) {
      this.catCategoryId = catCategoryId;
   }

   public String getRtnNumber() {
      return rtnNumber;
   }

   public void setRtnNumber(String rtnNumber) {
      this.rtnNumber = rtnNumber;
   }

   public String getRtnDescription() {
      return rtnDescription;
   }

   public void setRtnDescription(String rtnDescription) {
      this.rtnDescription = rtnDescription;
   }

   public Long getStsStatusId() {
      return stsStatusId;
   }

   public void setStsStatusId(Long stsStatusId) {
      this.stsStatusId = stsStatusId;
   }

   public Date getRtnDate() {
      return rtnDate;
   }

   public void setRtnDate(Date rtnDate) {
      this.rtnDate = rtnDate;
   }

   public String getRtnUser() {
      return rtnUser;
   }

   public void setRtnUser(String rtnUser) {
      this.rtnUser = rtnUser;
   }

}