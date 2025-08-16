package com.oracle.loanapp.dto.response;

import com.oracle.loanapp.entity.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanApplicationResponseDto {
    private Long id;
    private Long personalId;
    private Long propertyId;
    private Long incomeId;
//    private Long documentId;
    private Long custId;

    private BigDecimal requestedLoanAmount;
    private Integer loanTenureYears;
    private Double interestRate;
    private LoanStatus applicationStatus;
    private String rejectionReason;
    private LocalDate applicationDate;
    private LocalDate approvalDate;

    public LoanApplicationResponseDto() {}

    public LoanApplicationResponseDto(Long id, Long custId, Long personalId, Long propertyId, Long incomeId, BigDecimal requestedLoanAmount, Integer loanTenureYears, Double interestRate, LoanStatus applicationStatus, String rejectionReason, LocalDate applicationDate, LocalDate approvalDate) {
        this.id = id;
        this.custId = custId;
        this.personalId = personalId;
        this.propertyId = propertyId;
        this.incomeId = incomeId;
//        this.documentId = documentId;
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
    public Long getPersonalId() { return personalId; }
    public void setPersonalId(Long personalId) { this.personalId = personalId; }
    public Long getPropertyId() { return propertyId; }
    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }
    public Long getIncomeId() { return incomeId; }
    public void setIncomeId(Long incomeId) { this.incomeId = incomeId; }
//    public Long getDocumentId() { return documentId; }
//    public void setDocumentId(Long documentId) { this.documentId = documentId; }
    public BigDecimal getRequestedLoanAmount() { return requestedLoanAmount; }
    public void setRequestedLoanAmount(BigDecimal requestedLoanAmount) { this.requestedLoanAmount = requestedLoanAmount; }
    public Integer getLoanTenureYears() { return loanTenureYears; }
    public void setLoanTenureYears(Integer loanTenureYears) { this.loanTenureYears = loanTenureYears; }
    public Double getInterestRate() { return interestRate; }
    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }
    public LoanStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(LoanStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }
    public LocalDate getApprovalDate() { return approvalDate; }
    public void setApprovalDate(LocalDate approvalDate) { this.approvalDate = approvalDate; }
}
