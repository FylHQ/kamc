package ru.devag.kamc.model;

import javax.persistence.*;

@Entity
@Table(name="i3_ext_attributes")
public class I3ExtAttributes {

   @Column(name = "i3exa_ext_attribute_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3exa_name", nullable = false)
   private String exaName;

   @Column(name = "i3exa_code", nullable = false)
   private String exaCode;

   @Column(name = "i3exa_data_type", nullable = false)
   private String exaDataType;

   @Column(name = "i3exa_reference")
   private String exaReference;

   @Column(name = "i3exa_ref_sql")
   private String exaRefSql;

   @Column(name = "i3exa_parent_id", nullable = false)
   private Long exaParentId;

   @Column(name = "i3exa_parent_table")
   private String exaParentTable;

   @Column(name = "i3exa_parent_name")
   private String exaParentName;

   @Column(name = "i3dea_d_exa_type_id", nullable = false)
   private Long deaDExaTypeId;

   @Column(name = "i3exa_order")
   private Long exaOrder;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getExaName() {
      return exaName;
   }

   public void setExaName(String exaName) {
      this.exaName = exaName;
   }

   public String getExaCode() {
      return exaCode;
   }

   public void setExaCode(String exaCode) {
      this.exaCode = exaCode;
   }

   public String getExaDataType() {
      return exaDataType;
   }

   public void setExaDataType(String exaDataType) {
      this.exaDataType = exaDataType;
   }

   public String getExaReference() {
      return exaReference;
   }

   public void setExaReference(String exaReference) {
      this.exaReference = exaReference;
   }

   public String getExaRefSql() {
      return exaRefSql;
   }

   public void setExaRefSql(String exaRefSql) {
      this.exaRefSql = exaRefSql;
   }

   public Long getExaParentId() {
      return exaParentId;
   }

   public void setExaParentId(Long exaParentId) {
      this.exaParentId = exaParentId;
   }

   public String getExaParentTable() {
      return exaParentTable;
   }

   public void setExaParentTable(String exaParentTable) {
      this.exaParentTable = exaParentTable;
   }

   public String getExaParentName() {
      return exaParentName;
   }

   public void setExaParentName(String exaParentName) {
      this.exaParentName = exaParentName;
   }

   public Long getDeaDExaTypeId() {
      return deaDExaTypeId;
   }

   public void setDeaDExaTypeId(Long deaDExaTypeId) {
      this.deaDExaTypeId = deaDExaTypeId;
   }

   public Long getExaOrder() {
      return exaOrder;
   }

   public void setExaOrder(Long exaOrder) {
      this.exaOrder = exaOrder;
   }

}