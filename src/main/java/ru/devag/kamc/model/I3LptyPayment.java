package ru.devag.kamc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="i3_lpty_payment")
public class I3LptyPayment {

   @Column(name = "i3pmt_payment_id", nullable = false)
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "I3_SEQ")
   @SequenceGenerator(sequenceName = "i3_allocateid", allocationSize = 1, name = "I3_SEQ")
   private Long id;

   @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
   @JoinColumn(name = "i3ptl_protocol_id", nullable = false)
   private I3LptyProtocol proto;

   @Column(name = "i3pmt_number", nullable = false)
   private String pmtNumber;

   @Column(name = "i3pmt_payment_start", nullable = false)
   private Date pmtPaymentStart;

   @Column(name = "i3pmt_payment_end", nullable = false)
   private Date pmtPaymentEnd;

   @Column(name = "i3pmt_expected_val", nullable = false)
   private Double pmtExpectedVal;

   @Column(name = "i3pmt_recieved_val")
   private Double pmtRecievedVal;

   @Column(name = "i3pmt_date")
   private Date pmtDate;

   @Column(name = "i3pmt_document")
   private String pmtDocument;

   @Column(name = "i3pmt_fined_val")
   private Double pmtFinedVal;

   @Column(name = "i3pmt_fine_date")
   private Date pmtFineDate;

   @Column(name = "i3pmt_fine_period")
   private Long pmtFinePeriod;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public I3LptyProtocol getProto() {
      return proto;
   }

   public void setProto(I3LptyProtocol proto) {
      this.proto = proto;
   }

   public String getPmtNumber() {
      return pmtNumber;
   }

   public void setPmtNumber(String pmtNumber) {
      this.pmtNumber = pmtNumber;
   }

   public Date getPmtPaymentStart() {
      return pmtPaymentStart;
   }

   public void setPmtPaymentStart(Date pmtPaymentStart) {
      this.pmtPaymentStart = pmtPaymentStart;
   }

   public Date getPmtPaymentEnd() {
      return pmtPaymentEnd;
   }

   public void setPmtPaymentEnd(Date pmtPaymentEnd) {
      this.pmtPaymentEnd = pmtPaymentEnd;
   }

   public Double getPmtExpectedVal() {
      return pmtExpectedVal;
   }

   public void setPmtExpectedVal(Double pmtExpectedVal) {
      this.pmtExpectedVal = pmtExpectedVal;
   }

   public Double getPmtRecievedVal() {
      return pmtRecievedVal;
   }

   public void setPmtRecievedVal(Double pmtRecievedVal) {
      this.pmtRecievedVal = pmtRecievedVal;
   }

   public Date getPmtDate() {
      return pmtDate;
   }

   public void setPmtDate(Date pmtDate) {
      this.pmtDate = pmtDate;
   }

   public String getPmtDocument() {
      return pmtDocument;
   }

   public void setPmtDocument(String pmtDocument) {
      this.pmtDocument = pmtDocument;
   }

   public Double getPmtFinedVal() {
      return pmtFinedVal;
   }

   public void setPmtFinedVal(Double pmtFinedVal) {
      this.pmtFinedVal = pmtFinedVal;
   }

   public Date getPmtFineDate() {
      return pmtFineDate;
   }

   public void setPmtFineDate(Date pmtFineDate) {
      this.pmtFineDate = pmtFineDate;
   }

   public Long getPmtFinePeriod() {
      return pmtFinePeriod;
   }

   public void setPmtFinePeriod(Long pmtFinePeriod) {
      this.pmtFinePeriod = pmtFinePeriod;
   }

}