package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoanApplicationDTO {

    @NotBlank(message = "Loan status must not be blank")  // For example: "APPROVED" or "REJECTED"
    private String loanStatus;

    // rejectionReason can be null if loanStatus is APPROVED
    private String rejectionReason;

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
