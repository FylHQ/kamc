package ru.devag.kamc.model;

import javax.persistence.*;

@Entity(name="i3_category")
public class I3Category {

   @Column(name = "i3cat_category_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3cat_parent_id")
   private Long catParentId;

   @Column(name = "i3cat_code", nullable = false)
   private String catCode;

   @Column(name = "i3cat_table")
   private String catTable;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getCatParentId() {
      return catParentId;
   }

   public void setCatParentId(Long catParentId) {
      this.catParentId = catParentId;
   }

   public String getCatCode() {
      return catCode;
   }

   public void setCatCode(String catCode) {
      this.catCode = catCode;
   }

   public String getCatTable() {
      return catTable;
   }

   public void setCatTable(String catTable) {
      this.catTable = catTable;
   }

}