package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;

@Entity(name="i3_bldn_component")
public class I3BldnComponent {

   @Column(name = "i3bld_bldn_component_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3bld_storey_num")
   private String bldStoreyNum;

   @Column(name = "i3bld_undeground_num")
   private String bldUndegroundNum;

   @Column(name = "i3lnd_land_component_id", nullable = false)
   private Long lndLandComponentId;

   @Column(name = "i3cfv_classifier_value_id", nullable = false)
   private Long cfvClassifierValueId;

   @Column(name = "i3bld_cadastral_number")
   private String bldCadastralNumber;

   @Column(name = "i3bld_dummy_number")
   private String bldDummyNumber;

   @Column(name = "i3bld_land_area")
   private Double bldLandArea;

   @Column(name = "i3bld_common_area")
   private Double bldCommonArea;

   @Column(name = "i3bld_living_area")
   private Double bldLivingArea;

   @Column(name = "i3bldn_quarters")
   private Long bldnQuarters;

   @Column(name = "i3bldn_bti_inv_number")
   private String bldnBtiInvNumber;

   @Column(name = "i3bldn_bti_date")
   private Date bldnBtiDate;

   @Column(name = "i3bldn_bti_letter")
   private String bldnBtiLetter;

   @Column(name = "i3bld_ismemorial", nullable = false)
   private String bldIsmemorial;

   @Column(name = "i3bld_when_constructed")
   private Date bldWhenConstructed;

   @Column(name = "i3bld_isfinish", nullable = false)
   private String bldIsfinish;

   @Column(name = "i3bldn_quarters_priv")
   private Long bldnQuartersPriv;

   @Column(name = "i3bld_iscontainer", nullable = false)
   private String bldIscontainer;

   @Column(name = "i3bld_construct_period")
   private String bldConstructPeriod;

   @Column(name = "i3bld_ktz_clf_id")
   private Long bldKtzClfId;

   @Column(name = "i3bld_kki_clf_id")
   private Long bldKkiClfId;

   @Column(name = "i3bld_kz_clf_id")
   private Long bldKzClfId;

   @Column(name = "i3bld_cadastral_info")
   private String bldCadastralInfo;

   @Column(name = "i3bld_cadastral_cost")
   private Double bldCadastralCost;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getBldStoreyNum() {
      return bldStoreyNum;
   }

   public void setBldStoreyNum(String bldStoreyNum) {
      this.bldStoreyNum = bldStoreyNum;
   }

   public String getBldUndegroundNum() {
      return bldUndegroundNum;
   }

   public void setBldUndegroundNum(String bldUndegroundNum) {
      this.bldUndegroundNum = bldUndegroundNum;
   }

   public Long getLndLandComponentId() {
      return lndLandComponentId;
   }

   public void setLndLandComponentId(Long lndLandComponentId) {
      this.lndLandComponentId = lndLandComponentId;
   }

   public Long getCfvClassifierValueId() {
      return cfvClassifierValueId;
   }

   public void setCfvClassifierValueId(Long cfvClassifierValueId) {
      this.cfvClassifierValueId = cfvClassifierValueId;
   }

   public String getBldCadastralNumber() {
      return bldCadastralNumber;
   }

   public void setBldCadastralNumber(String bldCadastralNumber) {
      this.bldCadastralNumber = bldCadastralNumber;
   }

   public String getBldDummyNumber() {
      return bldDummyNumber;
   }

   public void setBldDummyNumber(String bldDummyNumber) {
      this.bldDummyNumber = bldDummyNumber;
   }

   public Double getBldLandArea() {
      return bldLandArea;
   }

   public void setBldLandArea(Double bldLandArea) {
      this.bldLandArea = bldLandArea;
   }

   public Double getBldCommonArea() {
      return bldCommonArea;
   }

   public void setBldCommonArea(Double bldCommonArea) {
      this.bldCommonArea = bldCommonArea;
   }

   public Double getBldLivingArea() {
      return bldLivingArea;
   }

   public void setBldLivingArea(Double bldLivingArea) {
      this.bldLivingArea = bldLivingArea;
   }

   public Long getBldnQuarters() {
      return bldnQuarters;
   }

   public void setBldnQuarters(Long bldnQuarters) {
      this.bldnQuarters = bldnQuarters;
   }

   public String getBldnBtiInvNumber() {
      return bldnBtiInvNumber;
   }

   public void setBldnBtiInvNumber(String bldnBtiInvNumber) {
      this.bldnBtiInvNumber = bldnBtiInvNumber;
   }

   public Date getBldnBtiDate() {
      return bldnBtiDate;
   }

   public void setBldnBtiDate(Date bldnBtiDate) {
      this.bldnBtiDate = bldnBtiDate;
   }

   public String getBldnBtiLetter() {
      return bldnBtiLetter;
   }

   public void setBldnBtiLetter(String bldnBtiLetter) {
      this.bldnBtiLetter = bldnBtiLetter;
   }

   public String getBldIsmemorial() {
      return bldIsmemorial;
   }

   public void setBldIsmemorial(String bldIsmemorial) {
      this.bldIsmemorial = bldIsmemorial;
   }

   public Date getBldWhenConstructed() {
      return bldWhenConstructed;
   }

   public void setBldWhenConstructed(Date bldWhenConstructed) {
      this.bldWhenConstructed = bldWhenConstructed;
   }

   public String getBldIsfinish() {
      return bldIsfinish;
   }

   public void setBldIsfinish(String bldIsfinish) {
      this.bldIsfinish = bldIsfinish;
   }

   public Long getBldnQuartersPriv() {
      return bldnQuartersPriv;
   }

   public void setBldnQuartersPriv(Long bldnQuartersPriv) {
      this.bldnQuartersPriv = bldnQuartersPriv;
   }

   public String getBldIscontainer() {
      return bldIscontainer;
   }

   public void setBldIscontainer(String bldIscontainer) {
      this.bldIscontainer = bldIscontainer;
   }

   public String getBldConstructPeriod() {
      return bldConstructPeriod;
   }

   public void setBldConstructPeriod(String bldConstructPeriod) {
      this.bldConstructPeriod = bldConstructPeriod;
   }

   public Long getBldKtzClfId() {
      return bldKtzClfId;
   }

   public void setBldKtzClfId(Long bldKtzClfId) {
      this.bldKtzClfId = bldKtzClfId;
   }

   public Long getBldKkiClfId() {
      return bldKkiClfId;
   }

   public void setBldKkiClfId(Long bldKkiClfId) {
      this.bldKkiClfId = bldKkiClfId;
   }

   public Long getBldKzClfId() {
      return bldKzClfId;
   }

   public void setBldKzClfId(Long bldKzClfId) {
      this.bldKzClfId = bldKzClfId;
   }

   public String getBldCadastralInfo() {
      return bldCadastralInfo;
   }

   public void setBldCadastralInfo(String bldCadastralInfo) {
      this.bldCadastralInfo = bldCadastralInfo;
   }

   public Double getBldCadastralCost() {
      return bldCadastralCost;
   }

   public void setBldCadastralCost(Double bldCadastralCost) {
      this.bldCadastralCost = bldCadastralCost;
   }

}