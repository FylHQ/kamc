package ru.devag.kamc.model;

import javax.persistence.*;

@Entity
@Table(name="i3_obj_bst_trait")
public class I3ObjBstTrait {

   @Column(name = "i3obt_obj_bst_trait_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3obb_obj_bst_id", nullable = false)
   private Long obbObjBstId;

   @Column(name = "i3obt_is_partial", nullable = false)
   private String obtIsPartial;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getObbObjBstId() {
      return obbObjBstId;
   }

   public void setObbObjBstId(Long obbObjBstId) {
      this.obbObjBstId = obbObjBstId;
   }

   public String getObtIsPartial() {
      return obtIsPartial;
   }

   public void setObtIsPartial(String obtIsPartial) {
      this.obtIsPartial = obtIsPartial;
   }

}