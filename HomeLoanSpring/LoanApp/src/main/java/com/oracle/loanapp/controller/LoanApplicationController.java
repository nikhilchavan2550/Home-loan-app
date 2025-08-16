package com.oracle.loanapp.controller;

import com.oracle.loanapp.dto.request.LoanApplicationRequestDto;
import com.oracle.loanapp.dto.request.LoanApplicationStatusUpdateDto;
import com.oracle.loanapp.dto.request.RejectionReasonUpdateDto;
import com.oracle.loanapp.dto.response.LoanApplicationResponseDto;
import com.oracle.loanapp.service.LoanApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Loan Application", description = "Endpoints for managing loan applications")
@CrossOrigin(origins = "*")
public class LoanApplicationController {
    @Autowired
    private LoanApplicationService loanApplicationService;

    @Operation(summary = "Apply for a new loan")
    @PostMapping("/loan/apply")
    public ResponseEntity<LoanApplicationResponseDto> applyLoan(@RequestBody LoanApplicationRequestDto requestDto) {
        return ResponseEntity.ok(loanApplicationService.applyLoan(requestDto));
    }

    @Operation(summary = "Get all loan applications")
    @GetMapping("/admin/loan/all")
    public ResponseEntity<List<LoanApplicationResponseDto>> getAllApplications() {
        return ResponseEntity.ok(loanApplicationService.getAllApplications());
    }

    @Operation(summary = "Get loan application by ID")
    @GetMapping("/loan/{id}")
    public ResponseEntity<LoanApplicationResponseDto> getApplicationById(@PathVariable Long id) {
        return ResponseEntity.ok(loanApplicationService.getApplicationById(id));
    }

    @Operation(summary = "Get loan applications by customer ID")
    @GetMapping("/loan/by-customer/{custId}")
    public ResponseEntity<List<LoanApplicationResponseDto>> getApplicationsByCustomerId(@PathVariable Long custId) {
        return ResponseEntity.ok(loanApplicationService.getApplicationsByCustomerId(custId));
    }

    @Operation(summary = "Update loan application status (APPROVED/REJECTED)")
    @PutMapping("/admin/loan/status/{id}")
    public ResponseEntity<LoanApplicationResponseDto> updateApplicationStatus(@PathVariable Long id, @Valid @RequestBody LoanApplicationStatusUpdateDto statusUpdateDto) {
        return ResponseEntity.ok(loanApplicationService.updateApplicationStatus(id, statusUpdateDto));
    }

    @Operation(summary = "Delete a loan application")
    @DeleteMapping("/admin/loan/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        loanApplicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Update rejection reason of a loan application")
    @PatchMapping("/admin/loan/rejection-reason/{id}")
    public ResponseEntity<LoanApplicationResponseDto> updateRejectionReason(
            @PathVariable Long id,
            @Valid @RequestBody RejectionReasonUpdateDto rejectionReasonDto) {
        return ResponseEntity.ok(loanApplicationService.updateRejectionReason(id, rejectionReasonDto.getRejectionReason()));
    }


    


}
