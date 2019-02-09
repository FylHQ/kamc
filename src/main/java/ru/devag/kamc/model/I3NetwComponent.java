package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "i3_netw_component")
public class I3NetwComponent {

   @Column(name = "i3net_netw_component_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "i3lnd_land_component_id", nullable = false)
   private I3LandComponent land;

   @Column(name = "i3cfv_classifier_value_id", nullable = false)
   private Long cfvClassifierValueId;

   @Column(name = "i3net_invetory_num")
   private String netInvetoryNum;

   @Column(name = "i3net_length")
   private Double netLength;

   @Column(name = "i3net_isfinish", nullable = false)
   private String netIsfinish;

   @Column(name = "i3net_description")
   private String netDescription;

   @Column(name = "i3net_construct_period")
   private String netConstructPeriod;

   @Column(name = "i3net_implement_date")
   private Date netImplementDate;

   @Column(name = "i3net_bti_inv_number")
   private String netBtiInvNumber;

   @Column(name = "i3net_bti_date")
   private Date netBtiDate;

   @Column(name = "i3net_bti_letter")
   private String netBtiLetter;

   @Column(name = "i3net_cadastral_info")
   private String netCadastralInfo;

   @Column(name = "i3net_cadastral_cost")
   private Double netCadastralCost;

   @Column(name = "i3net_is_movable", nullable = false)
   private String netIsMovable;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getCfvClassifierValueId() {
      return cfvClassifierValueId;
   }

   public void setCfvClassifierValueId(Long cfvClassifierValueId) {
      this.cfvClassifierValueId = cfvClassifierValueId;
   }

   public String getNetInvetoryNum() {
      return netInvetoryNum;
   }

   public void setNetInvetoryNum(String netInvetoryNum) {
      this.netInvetoryNum = netInvetoryNum;
   }

   public Double getNetLength() {
      return netLength;
   }

   public void setNetLength(Double netLength) {
      this.netLength = netLength;
   }

   public String getNetIsfinish() {
      return netIsfinish;
   }

   public void setNetIsfinish(String netIsfinish) {
      this.netIsfinish = netIsfinish;
   }

   public String getNetDescription() {
      return netDescription;
   }

   public void setNetDescription(String netDescription) {
      this.netDescription = netDescription;
   }

   public String getNetConstructPeriod() {
      return netConstructPeriod;
   }

   public void setNetConstructPeriod(String netConstructPeriod) {
      this.netConstructPeriod = netConstructPeriod;
   }

   public Date getNetImplementDate() {
      return netImplementDate;
   }

   public void setNetImplementDate(Date netImplementDate) {
      this.netImplementDate = netImplementDate;
   }

   public String getNetBtiInvNumber() {
      return netBtiInvNumber;
   }

   public void setNetBtiInvNumber(String netBtiInvNumber) {
      this.netBtiInvNumber = netBtiInvNumber;
   }

   public Date getNetBtiDate() {
      return netBtiDate;
   }

   public void setNetBtiDate(Date netBtiDate) {
      this.netBtiDate = netBtiDate;
   }

   public String getNetBtiLetter() {
      return netBtiLetter;
   }

   public void setNetBtiLetter(String netBtiLetter) {
      this.netBtiLetter = netBtiLetter;
   }

   public String getNetCadastralInfo() {
      return netCadastralInfo;
   }

   public void setNetCadastralInfo(String netCadastralInfo) {
      this.netCadastralInfo = netCadastralInfo;
   }

   public Double getNetCadastralCost() {
      return netCadastralCost;
   }

   public void setNetCadastralCost(Double netCadastralCost) {
      this.netCadastralCost = netCadastralCost;
   }

   public String getNetIsMovable() {
      return netIsMovable;
   }

   public void setNetIsMovable(String netIsMovable) {
      this.netIsMovable = netIsMovable;
   }

   public I3LandComponent getLand() {
      return land;
   }

   public void setLand(I3LandComponent land) {
      this.land = land;
   }

}