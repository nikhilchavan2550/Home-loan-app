package com.oracle.loanapp.dto.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class LoanApplicationRequestDto {
    @NotNull(message = "personalId is required")
    private Long personalId;
    @NotNull(message = "custId is required")
    private Long custId;
    @NotNull(message = "propertyId is required")
    private Long propertyId;
    @NotNull(message = "incomeId is required")
    private Long incomeId;
//    @NotNull(message = "documentId is required")
//    private Long documentId;
    @NotNull(message = "requestedLoanAmount is required")
    private BigDecimal requestedLoanAmount;
    @NotNull(message = "loanTenureYears is required")
    private Integer loanTenureYears;
    @NotNull(message = "interestRate is required")
    private Double interestRate;

    public LoanApplicationRequestDto() {}

    public LoanApplicationRequestDto(Long personalId, Long propertyId, Long incomeId, BigDecimal requestedLoanAmount, Integer loanTenureYears, Double interestRate, Long custId) {
        this.personalId = personalId;
        this.propertyId = propertyId;
        this.incomeId = incomeId;
//        this.documentId = documentId;
        this.requestedLoanAmount = requestedLoanAmount;
        this.loanTenureYears = loanTenureYears;
        this.interestRate = interestRate;
        this.custId = custId;
        }

    public Long getPersonalId() { return personalId; }
    public void setPersonalId(Long personalId) { this.personalId = personalId; }
    
    public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

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
}
