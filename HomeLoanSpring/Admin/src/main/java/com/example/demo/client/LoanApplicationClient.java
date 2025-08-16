package com.example.demo.client;

import com.example.demo.dto.LoanApplicationDTO;
import com.example.demo.vo.LoanApplicationVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
    name = "loan-service",
    url = "http://localhost:8080" // Hardcoded URL to your friend's API
)
public interface LoanApplicationClient {

    // Get all loan applications
    @GetMapping("/api/admin/loan/all") //http://localhost:8083/admin/loan/all

    List<LoanApplicationVO> getAllApplications();

    // Get loan application by ID
    @GetMapping("/api/loan/{applicationId}")  //http://localhost:8083/api/loan/5
    LoanApplicationVO getApplicationById(@PathVariable("applicationId") long applicationId);

    @PutMapping("/api/admin/loan/status/{id}")
    void updateStatus(@PathVariable("id") long id, @RequestBody LoanApplicationDTO dto);

}
