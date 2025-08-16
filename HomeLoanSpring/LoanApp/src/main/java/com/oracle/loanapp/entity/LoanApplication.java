package com.oracle.loanapp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loan_application")
@SequenceGenerator(name = "loan_application_seq", sequenceName = "loan_application_seq", allocationSize = 1)
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_application_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_id", referencedColumnName = "id", nullable = false)
    private PersonalDetails personalDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", referencedColumnName = "id", nullable = false)
    private PropertyDetails propertyDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "income_id", referencedColumnName = "id", nullable = false)
    private IncomeDetails incomeDetails;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
//    private DocumentDetails documentDetails;

    @Column(name = "requested_loan_amount", nullable = false)
    private BigDecimal requestedLoanAmount;
    
    @Column(name = "custId", nullable = false)
    private Long custId;

    @Column(name = "loan_tenure_years", nullable = false)
    private Integer loanTenureYears;

    @Column(name = "interest_rate", nullable = false)
    private Double interestRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status", nullable = false)
    private LoanStatus applicationStatus;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    public LoanApplication() {}

    public LoanApplication(Long id,Long custId, PersonalDetails personalDetails, PropertyDetails propertyDetails, IncomeDetails incomeDetails, BigDecimal requestedLoanAmount, Integer loanTenureYears, Double interestRate, LoanStatus applicationStatus, String rejectionReason, LocalDate applicationDate, LocalDate approvalDate) {
        this.id = id;
        this.custId = custId;
        this.personalDetails = personalDetails;
        this.propertyDetails = propertyDetails;
        this.incomeDetails = incomeDetails;
//        this.documentDetails = documentDetails;
        this.requestedLoanAmount = requestedLoanAmount;
        this.loanTenureYears = loanTenureYears;
        this.interestRate = interestRate;
        this.applicationStatus = applicationStatus;
        this.rejectionReason = rejectionReason;
        this.applicationDate = applicationDate;
        this.approvalDate = approvalDate;
    }
    
    

    public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PersonalDetails getPersonalDetails() { return personalDetails; }
    public void setPersonalDetails(PersonalDetails personalDetails) { this.personalDetails = personalDetails; }

    public PropertyDetails getPropertyDetails() { return propertyDetails; }
    public void setPropertyDetails(PropertyDetails propertyDetails) { this.propertyDetails = propertyDetails; }

    public IncomeDetails getIncomeDetails() { return incomeDetails; }
    public void setIncomeDetails(IncomeDetails incomeDetails) { this.incomeDetails = incomeDetails; }

//    public DocumentDetails getDocumentDetails() { return documentDetails; }
//    public void setDocumentDetails(DocumentDetails documentDetails) { this.documentDetails = documentDetails; }

    public BigDecimal getRequestedLoanAmount() { return requestedLoanAmount; }
    public void setRequestedLoanAmount(BigDecimal requestedLoanAmount) { this.requestedLoanAmount = requestedLoanAmount; }

    public Integer getLoanTenureYears() { return loanTenureYears; }
    public void setLoanTenureYears(Integer loanTenureYears) { this.loanTenureYears = loanTenureYears; }

    public Double getInterestRate() { return interestRate; }
    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }

    public LoanStatus getApplicationStatus() { return applicationStatus; }
    public void setApplicationStatus(LoanStatus applicationStatus) { this.applicationStatus = applicationStatus; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }

    public LocalDate getApprovalDate() { return approvalDate; }
    public void setApprovalDate(LocalDate approvalDate) { this.approvalDate = approvalDate; }
}
