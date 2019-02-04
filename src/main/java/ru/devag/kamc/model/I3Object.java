package ru.devag.kamc.model;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "i3_object")
public class I3Object {

   @Column(name = "i3obj_object_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3cat_category_id", nullable = false)
   private Long catCategoryId;

   @Column(name = "i3obj_number", nullable = false)
   private String objNumber;

   @Column(name = "i3sts_status_id")
   private Long stsStatusId;

   @Column(name = "i3obj_description")
   private String objDescription;

   @Column(name = "i3obj_date")
   @CreationTimestamp
   @Temporal(TemporalType.TIMESTAMP)
   private Date objDate;

   @Column(name = "i3obj_user")
   @Transient
   private String objUser;

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

   public String getObjNumber() {
      return objNumber;
   }

   public void setObjNumber(String objNumber) {
      this.objNumber = objNumber;
   }

   public Long getStsStatusId() {
      return stsStatusId;
   }

   public void setStsStatusId(Long stsStatusId) {
      this.stsStatusId = stsStatusId;
   }

   public String getObjDescription() {
      return objDescription;
   }

   public void setObjDescription(String objDescription) {
      this.objDescription = objDescription;
   }

   public Date getObjDate() {
      return objDate;
   }

   public void setObjDate(Date objDate) {
      this.objDate = objDate;
   }

   public String getObjUser() {
      return objUser;
   }

   public void setObjUser(String objUser) {
      this.objUser = objUser;
   }

}