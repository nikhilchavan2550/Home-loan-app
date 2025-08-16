package com.example.demo.service;

import com.example.demo.client.LoanApplicationClient;
import com.example.demo.dto.LoanApplicationDTO;
import com.example.demo.vo.LoanApplicationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationClient client;

    public List<LoanApplicationVO> getAllLoanApplications() {
        System.out.println("Calling friend's service via Feign client...");
        List<LoanApplicationVO> apps = client.getAllApplications();
        System.out.println("Response from friend: " + apps);
        return apps;
    }


    public LoanApplicationVO getLoanApplicationById(long applicationId) {
        return client.getApplicationById(applicationId);
    }

    public String changeApplicationStatus(long appId, String newStatus, String rejectionReason) {
        LoanApplicationDTO dto = new LoanApplicationDTO();
        dto.setLoanStatus(newStatus);
        dto.setRejectionReason("REJECTED".equalsIgnoreCase(newStatus) ? rejectionReason : null);
        
        client.updateStatus(appId, dto);
        return "Status updated successfully for Application ID: " + appId;
    }
}
