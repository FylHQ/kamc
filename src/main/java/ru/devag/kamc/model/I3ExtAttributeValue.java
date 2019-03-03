package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="i3_ext_attribute_value")
public class I3ExtAttributeValue {

   @Column(name = "i3exv_ext_attribute_value_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3exa_ext_attribute_id", nullable = false)
   private Long exaExtAttributeId;

   @Column(name = "i3exv_com_id", nullable = false)
   private Long exvComId;

   @Column(name = "i3exv_text")
   private String exvText;

   @Column(name = "i3exv_number")
   private Long exvNumber;

   @Column(name = "i3exv_date")
   private Date exvDate;

   @Column(name = "i3exv_owner")
   @Transient
   private String exvOwner;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getExaExtAttributeId() {
      return exaExtAttributeId;
   }

   public void setExaExtAttributeId(Long exaExtAttributeId) {
      this.exaExtAttributeId = exaExtAttributeId;
   }

   public Long getExvComId() {
      return exvComId;
   }

   public void setExvComId(Long exvComId) {
      this.exvComId = exvComId;
   }

   public String getExvText() {
      return exvText;
   }

   public void setExvText(String exvText) {
      this.exvText = exvText;
   }

   public Long getExvNumber() {
      return exvNumber;
   }

   public void setExvNumber(Long exvNumber) {
      this.exvNumber = exvNumber;
   }

   public Date getExvDate() {
      return exvDate;
   }

   public void setExvDate(Date exvDate) {
      this.exvDate = exvDate;
   }

   public String getExvOwner() {
      return exvOwner;
   }

   public void setExvOwner(String exvOwner) {
      this.exvOwner = exvOwner;
   }

}