package com.oracle.loanapp.service.impl;

import com.oracle.loanapp.dto.request.LoanApplicationRequestDto;
import com.oracle.loanapp.dto.request.LoanApplicationStatusUpdateDto;
import com.oracle.loanapp.dto.response.LoanApplicationResponseDto;
import com.oracle.loanapp.entity.*;
import com.oracle.loanapp.exception.ResourceNotFoundException;
import com.oracle.loanapp.repository.*;
import com.oracle.loanapp.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LoanApplicationServiceImpl implements LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;
    @Autowired
    private PersonalDetailsRepository personalDetailsRepository;
    @Autowired
    private PropertyDetailsRepository propertyDetailsRepository;
    @Autowired
    private IncomeDetailsRepository incomeDetailsRepository;
    


//    @Autowired
////    private DocumentDetailsRepository documentDetailsRepository;

    @Override
    public LoanApplicationResponseDto applyLoan(LoanApplicationRequestDto requestDto) {
        PersonalDetails personal = personalDetailsRepository.findById(requestDto.getPersonalId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid personalId: " + requestDto.getPersonalId()));
        PropertyDetails property = propertyDetailsRepository.findById(requestDto.getPropertyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid propertyId: " + requestDto.getPropertyId()));
        IncomeDetails income = incomeDetailsRepository.findById(requestDto.getIncomeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid incomeId: " + requestDto.getIncomeId()));
//        DocumentDetails document = documentDetailsRepository.findById(requestDto.getDocumentId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid documentId: " + requestDto.getDocumentId()));
       

        
        LoanApplication entity = new LoanApplication();
        entity.setCustId(requestDto.getCustId()); // ✅ Set custId
        entity.setPersonalDetails(personal);
        entity.setPropertyDetails(property);
        entity.setIncomeDetails(income);
//        entity.setDocumentDetails(document);
        entity.setRequestedLoanAmount(requestDto.getRequestedLoanAmount());
        entity.setLoanTenureYears(requestDto.getLoanTenureYears());
        entity.setInterestRate(requestDto.getInterestRate());
        entity.setApplicationStatus(LoanStatus.PENDING);
        entity.setApplicationDate(LocalDate.now());
        entity.setApprovalDate(null);

        LoanApplication saved = loanApplicationRepository.save(entity);
        return convertToDto(saved);
    }

    @Override
    public List<LoanApplicationResponseDto> getAllApplications() {
        return loanApplicationRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<LoanApplicationResponseDto> getApplicationsByCustomerId(Long custId) {
        List<LoanApplication> applications = loanApplicationRepository.findByCustIdOrderByIdDesc(custId);

        return applications.stream()
        	    .map(this::convertToDto)
        	    .collect(Collectors.toList());

    }
    
    


    @Override
    public LoanApplicationResponseDto getApplicationById(Long id) {
        LoanApplication entity = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan application not found: " + id));
        return convertToDto(entity);
    }

    @Override
    public LoanApplicationResponseDto updateApplicationStatus(Long id, LoanApplicationStatusUpdateDto statusUpdateDto) {
        LoanApplication loanApplication = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan application not found"));

        loanApplication.setApplicationStatus(statusUpdateDto.getLoanStatus());

        if (statusUpdateDto.getLoanStatus() == LoanStatus.APPROVED) {
            loanApplication.setApprovalDate(LocalDate.now());
            loanApplication.setRejectionReason(null);
        } else if (statusUpdateDto.getLoanStatus() == LoanStatus.REJECTED) {
            loanApplication.setApprovalDate(null);
            loanApplication.setRejectionReason(statusUpdateDto.getRejectionReason());
        } else {
            loanApplication.setApprovalDate(null);
            loanApplication.setRejectionReason(null);
        }

        loanApplicationRepository.save(loanApplication);
        return convertToDto(loanApplication);
    }

    @Override
    public void deleteApplication(Long id) {
        loanApplicationRepository.deleteById(id);
    }

    private LoanApplicationResponseDto convertToDto(LoanApplication loanApplication) {
        return new LoanApplicationResponseDto(
                loanApplication.getId(),
                loanApplication.getCustId(), // ✅ Include custId in DTO conversion
                loanApplication.getPersonalDetails().getId(),
                loanApplication.getPropertyDetails().getId(),
                loanApplication.getIncomeDetails().getId(),
//                loanApplication.getDocumentDetails().getId(),
                loanApplication.getRequestedLoanAmount(),
                loanApplication.getLoanTenureYears(),
                loanApplication.getInterestRate(),
                loanApplication.getApplicationStatus(),
                loanApplication.getRejectionReason(),
                loanApplication.getApplicationDate(),
                loanApplication.getApprovalDate()
        );
    }
    
    @Override
    public LoanApplicationResponseDto updateRejectionReason(Long id, String rejectionReason) {
        LoanApplication application = loanApplicationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Loan application not found with id " + id));

        application.setRejectionReason(rejectionReason);
        loanApplicationRepository.save(application);

        return convertToDto(application);
    }



}
