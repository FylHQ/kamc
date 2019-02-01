package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;

@Entity(name="i3_status")
public class I3Status {

   @Column(name = "i3sts_status_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3sts_code")
   private String stsCode;

   @Column(name = "i3sts_back_color")
   private Long stsBackColor;

   @Column(name = "i3sts_text_color")
   private Long stsTextColor;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getStsCode() {
      return stsCode;
   }

   public void setStsCode(String stsCode) {
      this.stsCode = stsCode;
   }

   public Long getStsBackColor() {
      return stsBackColor;
   }

   public void setStsBackColor(Long stsBackColor) {
      this.stsBackColor = stsBackColor;
   }

   public Long getStsTextColor() {
      return stsTextColor;
   }

   public void setStsTextColor(Long stsTextColor) {
      this.stsTextColor = stsTextColor;
   }

}