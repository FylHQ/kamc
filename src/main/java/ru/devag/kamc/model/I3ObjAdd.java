package ru.devag.kamc.model;

import javax.persistence.*;

@Entity
@Table(name="i3_obj_add")
public class I3ObjAdd {

   @Column(name = "i3oba_obj_add_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3add_address_id", nullable = false)
   private Long addAddressId;

   @Column(name = "i3obj_object_id", nullable = false)
   private Long objObjectId;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getAddAddressId() {
      return addAddressId;
   }

   public void setAddAddressId(Long addAddressId) {
      this.addAddressId = addAddressId;
   }

   public Long getObjObjectId() {
      return objObjectId;
   }

   public void setObjObjectId(Long objObjectId) {
      this.objObjectId = objObjectId;
   }

}