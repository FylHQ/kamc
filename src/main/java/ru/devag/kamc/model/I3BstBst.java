package ru.devag.kamc.model;

import javax.persistence.*;

@Entity
@Table(name="i3_bst_bst")
public class I3BstBst {

   @Column(name = "i3bsb_bst_bst_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3bsb_child_id", nullable = false)
   private Long bsbChildId;

   @Column(name = "i3bst_parent_id", nullable = false)
   private Long bstParentId;

   @Column(name = "i3bsb_type", nullable = false)
   private Long bsbType;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getBsbChildId() {
      return bsbChildId;
   }

   public void setBsbChildId(Long bsbChildId) {
      this.bsbChildId = bsbChildId;
   }

   public Long getBstParentId() {
      return bstParentId;
   }

   public void setBstParentId(Long bstParentId) {
      this.bstParentId = bstParentId;
   }

   public Long getBsbType() {
      return bsbType;
   }

   public void setBsbType(Long bsbType) {
      this.bsbType = bsbType;
   }

}