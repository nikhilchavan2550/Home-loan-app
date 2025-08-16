package com.example.demo.vo;

import java.util.Date;

public class LoanApplicationVO {

    private int id;
    private int personalId;
    private int propertyId;
    private int incomeId;
    private int documentId;
    private double requestedLoanAmount;
    private int loanTenureYears;
    private double interestRate;
    private String applicationStatus;
    private String rejectionReason;
    private Date applicationDate;
    private Date approvalDate;
    
    private long custId;

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getPersonalId() {
        return personalId;
    }
    public void setPersonalId(int personalId) {
        this.personalId = personalId;
    }

    public int getPropertyId() {
        return propertyId;
    }
    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getIncomeId() {
        return incomeId;
    }
    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public int getDocumentId() {
        return documentId;
    }
    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public double getRequestedLoanAmount() {
        return requestedLoanAmount;
    }
    public void setRequestedLoanAmount(double requestedLoanAmount) {
        this.requestedLoanAmount = requestedLoanAmount;
    }

    public int getLoanTenureYears() {
        return loanTenureYears;
    }
    public void setLoanTenureYears(int loanTenureYears) {
        this.loanTenureYears = loanTenureYears;
    }

    public double getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(double interestRate) {
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

    public Date getApplicationDate() {
        return applicationDate;
    }
    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }
    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }
}
