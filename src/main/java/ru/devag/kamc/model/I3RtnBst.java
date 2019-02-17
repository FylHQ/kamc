package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="i3_rtn_bst")
public class I3RtnBst {

   @Column(name = "i3rbs_rtn_bst_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3rtn_relation_id", nullable = false)
   private Long rtnRelationId;

   @Column(name = "i3bst_basement_id", nullable = false)
   private Long bstBasementId;

   @Column(name = "i3rbs_type", nullable = false)
   private Long rbsType;


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

   public Long getBstBasementId() {
      return bstBasementId;
   }

   public void setBstBasementId(Long bstBasementId) {
      this.bstBasementId = bstBasementId;
   }

   public Long getRbsType() {
      return rbsType;
   }

   public void setRbsType(Long rbsType) {
      this.rbsType = rbsType;
   }

}