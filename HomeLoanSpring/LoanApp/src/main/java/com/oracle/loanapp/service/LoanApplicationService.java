package com.oracle.loanapp.service;

import com.oracle.loanapp.dto.request.LoanApplicationRequestDto;
import com.oracle.loanapp.dto.response.LoanApplicationResponseDto;
import java.util.List;

public interface LoanApplicationService {
    LoanApplicationResponseDto applyLoan(LoanApplicationRequestDto requestDto);
    List<LoanApplicationResponseDto> getAllApplications();
    LoanApplicationResponseDto getApplicationById(Long id);
    List<LoanApplicationResponseDto> getApplicationsByCustomerId(Long custId);
    LoanApplicationResponseDto updateRejectionReason(Long id, String rejectionReason);



    void deleteApplication(Long id);

    LoanApplicationResponseDto updateApplicationStatus(Long id, com.oracle.loanapp.dto.request.LoanApplicationStatusUpdateDto statusUpdateDto);
}
