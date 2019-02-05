package ru.devag.kamc.model;

import javax.persistence.*;

@Entity(name="i3_obj_bst")
public class I3ObjBst {

   @Column(name = "i3obb_obj_bst_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3bst_basement_id", nullable = false)
   private Long bstBasementId;

   @Column(name = "i3obj_object_id", nullable = false)
   private Long objObjectId;

   @Column(name = "i3obb_type", nullable = false)
   private Long obbType;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getBstBasementId() {
      return bstBasementId;
   }

   public void setBstBasementId(Long bstBasementId) {
      this.bstBasementId = bstBasementId;
   }

   public Long getObjObjectId() {
      return objObjectId;
   }

   public void setObjObjectId(Long objObjectId) {
      this.objObjectId = objObjectId;
   }

   public Long getObbType() {
      return obbType;
   }

   public void setObbType(Long obbType) {
      this.obbType = obbType;
   }

}