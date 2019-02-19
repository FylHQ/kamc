package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name="i3_cmpy_component")
public class I3CmpyComponent {

   @Column(name = "i3cmp_cmpy_component_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3cfv_classifier_value_id")
   private Long cfvClassifierValueId;

   @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
   @JoinColumn(name = "i3sbj_subject_id", nullable = false)
   private I3Subject sbj;

   @Column(name = "i3cmp_okpo")
   private String cmpOkpo;

   @Column(name = "i3cmp_okonh")
   private String cmpOkonh;

   @Column(name = "i3cmp_inn")
   private String cmpInn;

   @Column(name = "t3cmp_short_name")
   private String T3cmpShortName;

   @Column(name = "i3cmp_okogu")
   private String cmpOkogu;

   @Column(name = "i3cmp_okato")
   private String cmpOkato;

   @Column(name = "i3cmp_okved")
   private String cmpOkved;

   @Column(name = "i3cmp_okfs")
   private String cmpOkfs;

   @Column(name = "i3cmp_okopf")
   private String cmpOkopf;

   @Column(name = "i3cmp_kpp")
   private String cmpKpp;

   @Column(name = "i3cmp_reg_num")
   private String cmpRegNum;

   @Column(name = "i3cmp_reg_date")
   private Date cmpRegDate;

   @Column(name = "i3cmp_reg_organ")
   private String cmpRegOrgan;

   @Column(name = "i3cmp_calc_account")
   private String cmpCalcAccount;

   @Column(name = "i3cmp_corr_account")
   private String cmpCorrAccount;

   @Column(name = "i3cmp_bik")
   private String cmpBik;

   @Column(name = "i3cfv_subjrf_id")
   private Long cfvSubjrfId;

   @Column(name = "i3cmp_reg_number")
   private String cmpRegNumber;

   @Column(name = "i3cmp_ogrn")
   private String cmpOgrn;

   @Column(name = "i3cfv_activity_id")
   private Long cfvActivityId;

   @Column(name = "i3cmp_prop_cost_a")
   private Double cmpPropCostA;

   @Column(name = "i3cmp_first_date")
   private Date cmpFirstDate;

   @Column(name = "i3cmp_first_number")
   private String cmpFirstNumber;

   @Column(name = "i3cmp_first_doc")
   private String cmpFirstDoc;

   @Column(name = "i3cmp_ogrn_date")
   private Date cmpOgrnDate;

   @Column(name = "i3cmp_isbank")
   private String cmpIsbank;

   @Column(name = "i3cmp_istaxfree", nullable = false)
   private String cmpIstaxfree;

   @Column(name = "i3cmp_reg_organ_id")
   private Long cmpRegOrganId;

   @Column(name = "i3cfv_okved_id")
   private Long cfvOkvedId;

   @Column(name = "i3cmp_authorized_fund")
   private Long cmpAuthorizedFund;

   @Column(name = "i3cmp_number_of_employees")
   private Long cmpNumberOfEmployees;

   @Column(name = "i3cmp_receivership_start")
   private Date cmpReceivershipStart;

   @Column(name = "i3cmp_receivership_end")
   private Date cmpReceivershipEnd;

   @Column(name = "i3cmp_date_of_elimination")
   private Date cmpDateOfElimination;

   @Column(name = "i3cmp_authorized_fund_part")
   private Long cmpAuthorizedFundPart;

   @Column(name = "i3cmp_authorized_fund_percent")
   private Long cmpAuthorizedFundPercent;

   @Column(name = "i3cmp_oktmo")
   private String cmpOktmo;

   @Column(name = "i3cmp_oktmo_id")
   private Long cmpOktmoId;

   @Column(name = "i3cmp_cfv_industry_id")
   private Long cmpCfvIndustryId;

   @Column(name = "i3cmp_is_belong_to_registry", nullable = false)
   private String cmpIsBelongToRegistry;

   @Column(name = "i3cmp_origin_bst_id")
   private Long cmpOriginBstId;

   @Column(name = "i3cmp_cost10100_balance")
   private Double cmpCost10100Balance;

   @Column(name = "i3cmp_cost10100_remain")
   private Double cmpCost10100Remain;

   @Column(name = "i3cmp_elimination_bst_id")
   private Long cmpEliminationBstId;

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

   public I3Subject getSbj() {
      return sbj;
   }

   public void setSbj(I3Subject sbj) {
      this.sbj = sbj;
   }

   public String getCmpOkpo() {
      return cmpOkpo;
   }

   public void setCmpOkpo(String cmpOkpo) {
      this.cmpOkpo = cmpOkpo;
   }

   public String getCmpOkonh() {
      return cmpOkonh;
   }

   public void setCmpOkonh(String cmpOkonh) {
      this.cmpOkonh = cmpOkonh;
   }

   public String getCmpInn() {
      return cmpInn;
   }

   public void setCmpInn(String cmpInn) {
      this.cmpInn = cmpInn;
   }

   public String getT3cmpShortName() {
      return T3cmpShortName;
   }

   public void setT3cmpShortName(String T3cmpShortName) {
      this.T3cmpShortName = T3cmpShortName;
   }

   public String getCmpOkogu() {
      return cmpOkogu;
   }

   public void setCmpOkogu(String cmpOkogu) {
      this.cmpOkogu = cmpOkogu;
   }

   public String getCmpOkato() {
      return cmpOkato;
   }

   public void setCmpOkato(String cmpOkato) {
      this.cmpOkato = cmpOkato;
   }

   public String getCmpOkved() {
      return cmpOkved;
   }

   public void setCmpOkved(String cmpOkved) {
      this.cmpOkved = cmpOkved;
   }

   public String getCmpOkfs() {
      return cmpOkfs;
   }

   public void setCmpOkfs(String cmpOkfs) {
      this.cmpOkfs = cmpOkfs;
   }

   public String getCmpOkopf() {
      return cmpOkopf;
   }

   public void setCmpOkopf(String cmpOkopf) {
      this.cmpOkopf = cmpOkopf;
   }

   public String getCmpKpp() {
      return cmpKpp;
   }

   public void setCmpKpp(String cmpKpp) {
      this.cmpKpp = cmpKpp;
   }

   public String getCmpRegNum() {
      return cmpRegNum;
   }

   public void setCmpRegNum(String cmpRegNum) {
      this.cmpRegNum = cmpRegNum;
   }

   public Date getCmpRegDate() {
      return cmpRegDate;
   }

   public void setCmpRegDate(Date cmpRegDate) {
      this.cmpRegDate = cmpRegDate;
   }

   public String getCmpRegOrgan() {
      return cmpRegOrgan;
   }

   public void setCmpRegOrgan(String cmpRegOrgan) {
      this.cmpRegOrgan = cmpRegOrgan;
   }

   public String getCmpCalcAccount() {
      return cmpCalcAccount;
   }

   public void setCmpCalcAccount(String cmpCalcAccount) {
      this.cmpCalcAccount = cmpCalcAccount;
   }

   public String getCmpCorrAccount() {
      return cmpCorrAccount;
   }

   public void setCmpCorrAccount(String cmpCorrAccount) {
      this.cmpCorrAccount = cmpCorrAccount;
   }

   public String getCmpBik() {
      return cmpBik;
   }

   public void setCmpBik(String cmpBik) {
      this.cmpBik = cmpBik;
   }

   public Long getCfvSubjrfId() {
      return cfvSubjrfId;
   }

   public void setCfvSubjrfId(Long cfvSubjrfId) {
      this.cfvSubjrfId = cfvSubjrfId;
   }

   public String getCmpRegNumber() {
      return cmpRegNumber;
   }

   public void setCmpRegNumber(String cmpRegNumber) {
      this.cmpRegNumber = cmpRegNumber;
   }

   public String getCmpOgrn() {
      return cmpOgrn;
   }

   public void setCmpOgrn(String cmpOgrn) {
      this.cmpOgrn = cmpOgrn;
   }

   public Long getCfvActivityId() {
      return cfvActivityId;
   }

   public void setCfvActivityId(Long cfvActivityId) {
      this.cfvActivityId = cfvActivityId;
   }

   public Double getCmpPropCostA() {
      return cmpPropCostA;
   }

   public void setCmpPropCostA(Double cmpPropCostA) {
      this.cmpPropCostA = cmpPropCostA;
   }

   public Date getCmpFirstDate() {
      return cmpFirstDate;
   }

   public void setCmpFirstDate(Date cmpFirstDate) {
      this.cmpFirstDate = cmpFirstDate;
   }

   public String getCmpFirstNumber() {
      return cmpFirstNumber;
   }

   public void setCmpFirstNumber(String cmpFirstNumber) {
      this.cmpFirstNumber = cmpFirstNumber;
   }

   public String getCmpFirstDoc() {
      return cmpFirstDoc;
   }

   public void setCmpFirstDoc(String cmpFirstDoc) {
      this.cmpFirstDoc = cmpFirstDoc;
   }

   public Date getCmpOgrnDate() {
      return cmpOgrnDate;
   }

   public void setCmpOgrnDate(Date cmpOgrnDate) {
      this.cmpOgrnDate = cmpOgrnDate;
   }

   public String getCmpIsbank() {
      return cmpIsbank;
   }

   public void setCmpIsbank(String cmpIsbank) {
      this.cmpIsbank = cmpIsbank;
   }

   public String getCmpIstaxfree() {
      return cmpIstaxfree;
   }

   public void setCmpIstaxfree(String cmpIstaxfree) {
      this.cmpIstaxfree = cmpIstaxfree;
   }

   public Long getCmpRegOrganId() {
      return cmpRegOrganId;
   }

   public void setCmpRegOrganId(Long cmpRegOrganId) {
      this.cmpRegOrganId = cmpRegOrganId;
   }

   public Long getCfvOkvedId() {
      return cfvOkvedId;
   }

   public void setCfvOkvedId(Long cfvOkvedId) {
      this.cfvOkvedId = cfvOkvedId;
   }

   public Long getCmpAuthorizedFund() {
      return cmpAuthorizedFund;
   }

   public void setCmpAuthorizedFund(Long cmpAuthorizedFund) {
      this.cmpAuthorizedFund = cmpAuthorizedFund;
   }

   public Long getCmpNumberOfEmployees() {
      return cmpNumberOfEmployees;
   }

   public void setCmpNumberOfEmployees(Long cmpNumberOfEmployees) {
      this.cmpNumberOfEmployees = cmpNumberOfEmployees;
   }

   public Date getCmpReceivershipStart() {
      return cmpReceivershipStart;
   }

   public void setCmpReceivershipStart(Date cmpReceivershipStart) {
      this.cmpReceivershipStart = cmpReceivershipStart;
   }

   public Date getCmpReceivershipEnd() {
      return cmpReceivershipEnd;
   }

   public void setCmpReceivershipEnd(Date cmpReceivershipEnd) {
      this.cmpReceivershipEnd = cmpReceivershipEnd;
   }

   public Date getCmpDateOfElimination() {
      return cmpDateOfElimination;
   }

   public void setCmpDateOfElimination(Date cmpDateOfElimination) {
      this.cmpDateOfElimination = cmpDateOfElimination;
   }

   public Long getCmpAuthorizedFundPart() {
      return cmpAuthorizedFundPart;
   }

   public void setCmpAuthorizedFundPart(Long cmpAuthorizedFundPart) {
      this.cmpAuthorizedFundPart = cmpAuthorizedFundPart;
   }

   public Long getCmpAuthorizedFundPercent() {
      return cmpAuthorizedFundPercent;
   }

   public void setCmpAuthorizedFundPercent(Long cmpAuthorizedFundPercent) {
      this.cmpAuthorizedFundPercent = cmpAuthorizedFundPercent;
   }

   public String getCmpOktmo() {
      return cmpOktmo;
   }

   public void setCmpOktmo(String cmpOktmo) {
      this.cmpOktmo = cmpOktmo;
   }

   public Long getCmpOktmoId() {
      return cmpOktmoId;
   }

   public void setCmpOktmoId(Long cmpOktmoId) {
      this.cmpOktmoId = cmpOktmoId;
   }

   public Long getCmpCfvIndustryId() {
      return cmpCfvIndustryId;
   }

   public void setCmpCfvIndustryId(Long cmpCfvIndustryId) {
      this.cmpCfvIndustryId = cmpCfvIndustryId;
   }

   public String getCmpIsBelongToRegistry() {
      return cmpIsBelongToRegistry;
   }

   public void setCmpIsBelongToRegistry(String cmpIsBelongToRegistry) {
      this.cmpIsBelongToRegistry = cmpIsBelongToRegistry;
   }

   public Long getCmpOriginBstId() {
      return cmpOriginBstId;
   }

   public void setCmpOriginBstId(Long cmpOriginBstId) {
      this.cmpOriginBstId = cmpOriginBstId;
   }

   public Double getCmpCost10100Balance() {
      return cmpCost10100Balance;
   }

   public void setCmpCost10100Balance(Double cmpCost10100Balance) {
      this.cmpCost10100Balance = cmpCost10100Balance;
   }

   public Double getCmpCost10100Remain() {
      return cmpCost10100Remain;
   }

   public void setCmpCost10100Remain(Double cmpCost10100Remain) {
      this.cmpCost10100Remain = cmpCost10100Remain;
   }

   public Long getCmpEliminationBstId() {
      return cmpEliminationBstId;
   }

   public void setCmpEliminationBstId(Long cmpEliminationBstId) {
      this.cmpEliminationBstId = cmpEliminationBstId;
   }

}