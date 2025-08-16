package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanApplicationResponseDto {
    private Long id;
    private Long personalId;
    private Long propertyId;
    private Long incomeId;
    private Long documentId;
    private Long custId;

    private Double requestedLoanAmount;
    private Integer loanTenureYears;
    private Double interestRate;
    private String applicationStatus;
    private String rejectionReason;
    private LocalDate applicationDate;
    private LocalDate approvalDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPersonalId() {
		return personalId;
	}
	public void setPersonalId(Long personalId) {
		this.personalId = personalId;
	}
	public Long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}
	public Long getIncomeId() {
		return incomeId;
	}
	public void setIncomeId(Long incomeId) {
		this.incomeId = incomeId;
	}
	public Long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public Double getRequestedLoanAmount() {
		return requestedLoanAmount;
	}
	public void setRequestedLoanAmount(Double requestedLoanAmount) {
		this.requestedLoanAmount = requestedLoanAmount;
	}
	public Integer getLoanTenureYears() {
		return loanTenureYears;
	}
	public void setLoanTenureYears(Integer loanTenureYears) {
		this.loanTenureYears = loanTenureYears;
	}
	public Double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public String getRejectionReason() {
		return rejectionReason;
	}
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
	public LocalDate getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(LocalDate applicationDate) {
		this.applicationDate = applicationDate;
	}
	public LocalDate getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(LocalDate approvalDate) {
		this.approvalDate = approvalDate;
	}
    
    
}
