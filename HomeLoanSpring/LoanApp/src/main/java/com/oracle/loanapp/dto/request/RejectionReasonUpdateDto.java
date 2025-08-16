package com.oracle.loanapp.dto.request;

import jakarta.validation.constraints.NotBlank;

public class RejectionReasonUpdateDto {
    
    @NotBlank(message = "Rejection reason cannot be blank")
    private String rejectionReason;

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
