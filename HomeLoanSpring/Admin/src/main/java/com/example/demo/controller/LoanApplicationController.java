package com.example.demo.controller;

import com.example.demo.dto.LoanApplicationDTO;
import com.example.demo.service.LoanApplicationService;
import com.example.demo.vo.LoanApplicationVO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") // Base API path
@CrossOrigin(origins = "http://127.0.0.1:5501", 
             allowedHeaders = "*",
             methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT},
             allowCredentials = "true") // Enable credentials
public class LoanApplicationController {

    private final LoanApplicationService service;

    public LoanApplicationController(LoanApplicationService service) {
        this.service = service;
    }

    @GetMapping("/admin/loan/all") // Note the leading slash
    public List<LoanApplicationVO> getAllLoans() {
        return service.getAllLoanApplications();
    }

    @GetMapping("/loan/{id}")
    public LoanApplicationVO getLoanById(@PathVariable long id) {
        return service.getLoanApplicationById(id);
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<String> updateStatus(
        @PathVariable long id,
        @RequestBody LoanApplicationDTO dto
    ) {
        String status = dto.getLoanStatus();
        String reason = dto.getRejectionReason();

        // TODO: Add real logic here (e.g., call service to update)

        return ResponseEntity.ok("Status updated successfully for application ID: " + id);
    }



}