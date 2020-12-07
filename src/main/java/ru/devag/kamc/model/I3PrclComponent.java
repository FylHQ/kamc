package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="i3_prcl_component")
public class I3PrclComponent {

   @Column(name = "i3prc_prcl_component_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3lnd_land_component_id", nullable = false)
   private Long lndLandComponentId;

   @Column(name = "i3prc_cadastral_number")
   private String prcCadastralNumber;

   @Column(name = "i3prc_dummy_number")
   private String prcDummyNumber;

   @Column(name = "i3prc_doc_area")
   private Double prcDocArea;

   @Column(name = "i3prc_doc_area_precession")
   private Long prcDocAreaPrecession;

   @Column(name = "i3prc_lea_zone_id")
   private Long prcLeaZoneId;

   @Column(name = "i3prc_lea_rate")
   private Double prcLeaRate;

   @Column(name = "i3prc_lea_row")
   private Double prcLeaRow;

   @Column(name = "i3prc_lea_col")
   private Double prcLeaCol;

   @Column(name = "i3prc_istubed")
   private String prcIstubed;

   @Column(name = "i3prc_cad_cost")
   private Double prcCadCost;

   @Column(name = "i3prc_cad_reg_date")
   private Date prcCadRegDate;

   @Column(name = "i3prc_cad_dereg_date")
   private Date prcCadDeregDate;

   @Column(name = "i3prc_note")
   private String prcNote;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getLndLandComponentId() {
      return lndLandComponentId;
   }

   public void setLndLandComponentId(Long lndLandComponentId) {
      this.lndLandComponentId = lndLandComponentId;
   }

   public String getPrcCadastralNumber() {
      return prcCadastralNumber;
   }

   public void setPrcCadastralNumber(String prcCadastralNumber) {
      this.prcCadastralNumber = prcCadastralNumber;
   }

   public String getPrcDummyNumber() {
      return prcDummyNumber;
   }

   public void setPrcDummyNumber(String prcDummyNumber) {
      this.prcDummyNumber = prcDummyNumber;
   }

   public Double getPrcDocArea() {
      return prcDocArea;
   }

   public void setPrcDocArea(Double prcDocArea) {
      this.prcDocArea = prcDocArea;
   }

   public Long getPrcDocAreaPrecession() {
      return prcDocAreaPrecession;
   }

   public void setPrcDocAreaPrecession(Long prcDocAreaPrecession) {
      this.prcDocAreaPrecession = prcDocAreaPrecession;
   }

   public Long getPrcLeaZoneId() {
      return prcLeaZoneId;
   }

   public void setPrcLeaZoneId(Long prcLeaZoneId) {
      this.prcLeaZoneId = prcLeaZoneId;
   }

   public Double getPrcLeaRate() {
      return prcLeaRate;
   }

   public void setPrcLeaRate(Double prcLeaRate) {
      this.prcLeaRate = prcLeaRate;
   }

   public Double getPrcLeaRow() {
      return prcLeaRow;
   }

   public void setPrcLeaRow(Double prcLeaRow) {
      this.prcLeaRow = prcLeaRow;
   }

   public Double getPrcLeaCol() {
      return prcLeaCol;
   }

   public void setPrcLeaCol(Double prcLeaCol) {
      this.prcLeaCol = prcLeaCol;
   }

   public String getPrcIstubed() {
      return prcIstubed;
   }

   public void setPrcIstubed(String prcIstubed) {
      this.prcIstubed = prcIstubed;
   }

   public Double getPrcCadCost() {
      return prcCadCost;
   }

   public void setPrcCadCost(Double prcCadCost) {
      this.prcCadCost = prcCadCost;
   }

   public Date getPrcCadRegDate() {
      return prcCadRegDate;
   }

   public void setPrcCadRegDate(Date prcCadRegDate) {
      this.prcCadRegDate = prcCadRegDate;
   }

   public Date getPrcCadDeregDate() {
      return prcCadDeregDate;
   }

   public void setPrcCadDeregDate(Date prcCadDeregDate) {
      this.prcCadDeregDate = prcCadDeregDate;
   }

   public String getPrcNote() {
      return prcNote;
   }

   public void setPrcNote(String prcNote) {
      this.prcNote = prcNote;
   }

}