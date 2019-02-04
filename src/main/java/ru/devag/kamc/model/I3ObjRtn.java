package ru.devag.kamc.model;

import javax.persistence.*;

@Entity
@Table(name="i3_obj_rtn")
public class I3ObjRtn {

   @Column(name = "i3obr_obj_rtn_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3rtn_relation_id", nullable = false)
   private Long rtnRelationId;

   @Column(name = "i3obj_object_id", nullable = false)
   private Long objObjectId;

   @Column(name = "i3obr_type", nullable = false)
   private Long obrType;


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

   public Long getObjObjectId() {
      return objObjectId;
   }

   public void setObjObjectId(Long objObjectId) {
      this.objObjectId = objObjectId;
   }

   public Long getObrType() {
      return obrType;
   }

   public void setObrType(Long obrType) {
      this.obrType = obrType;
   }

}