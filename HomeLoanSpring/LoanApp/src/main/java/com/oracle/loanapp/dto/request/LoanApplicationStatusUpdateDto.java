package com.oracle.loanapp.dto.request;

import com.oracle.loanapp.entity.LoanStatus;
import jakarta.validation.constraints.NotNull;

public class LoanApplicationStatusUpdateDto {

    @NotNull(message = "Loan status cannot be null")
    private LoanStatus loanStatus;
    private String rejectionReason;

    public LoanApplicationStatusUpdateDto() {
    }

    public LoanApplicationStatusUpdateDto(LoanStatus loanStatus, String rejectionReason) {
        this.loanStatus = loanStatus;
        this.rejectionReason = rejectionReason;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
