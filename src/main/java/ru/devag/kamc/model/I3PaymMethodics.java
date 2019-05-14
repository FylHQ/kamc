package ru.devag.kamc.model;

import javax.persistence.*;

@Entity
@Table(name="i3_paym_methodics")
public class I3PaymMethodics {

   @Column(name = "i3pam_paym_methodics_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @Column(name = "i3pam_name", nullable = false)
   private String pamName;

   @Column(name = "i3pam_code")
   private String pamCode;

   @Column(name = "i3pam_description", nullable = false)
   private String pamDescription;

   @Column(name = "i3pal_paym_algorithm_id", nullable = false)
   private Long palPaymAlgorithmId;

   @Column(name = "i3pam_can_use_any_previous", nullable = false)
   private String pamCanUseAnyPrevious;

   @Column(name = "i3pam_use_base_cost", nullable = false)
   private String pamUseBaseCost;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getPamName() {
      return pamName;
   }

   public void setPamName(String pamName) {
      this.pamName = pamName;
   }

   public String getPamCode() {
      return pamCode;
   }

   public void setPamCode(String pamCode) {
      this.pamCode = pamCode;
   }

   public String getPamDescription() {
      return pamDescription;
   }

   public void setPamDescription(String pamDescription) {
      this.pamDescription = pamDescription;
   }

   public Long getPalPaymAlgorithmId() {
      return palPaymAlgorithmId;
   }

   public void setPalPaymAlgorithmId(Long palPaymAlgorithmId) {
      this.palPaymAlgorithmId = palPaymAlgorithmId;
   }

   public String getPamCanUseAnyPrevious() {
      return pamCanUseAnyPrevious;
   }

   public void setPamCanUseAnyPrevious(String pamCanUseAnyPrevious) {
      this.pamCanUseAnyPrevious = pamCanUseAnyPrevious;
   }

   public String getPamUseBaseCost() {
      return pamUseBaseCost;
   }

   public void setPamUseBaseCost(String pamUseBaseCost) {
      this.pamUseBaseCost = pamUseBaseCost;
   }

}