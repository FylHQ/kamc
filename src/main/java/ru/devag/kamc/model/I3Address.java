package ru.devag.kamc.model;

import javax.persistence.*;

@Entity
@Table(name="i3_address")
public class I3Address {

   @Column(name = "i3add_address_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3add_parent_id")
   private Long addParentId;

   @Column(name = "i3adt_d_address_type_id", nullable = false)
   private Long adtDAddressTypeId;

   @Column(name = "i3add_address", nullable = false)
   private String addAddress;

   @Column(name = "i3add_zip_code")
   private String addZipCode;

   @Column(name = "i3add_kladr")
   private String addKladr;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getAddParentId() {
      return addParentId;
   }

   public void setAddParentId(Long addParentId) {
      this.addParentId = addParentId;
   }

   public Long getAdtDAddressTypeId() {
      return adtDAddressTypeId;
   }

   public void setAdtDAddressTypeId(Long adtDAddressTypeId) {
      this.adtDAddressTypeId = adtDAddressTypeId;
   }

   public String getAddAddress() {
      return addAddress;
   }

   public void setAddAddress(String addAddress) {
      this.addAddress = addAddress;
   }

   public String getAddZipCode() {
      return addZipCode;
   }

   public void setAddZipCode(String addZipCode) {
      this.addZipCode = addZipCode;
   }

   public String getAddKladr() {
      return addKladr;
   }

   public void setAddKladr(String addKladr) {
      this.addKladr = addKladr;
   }

}