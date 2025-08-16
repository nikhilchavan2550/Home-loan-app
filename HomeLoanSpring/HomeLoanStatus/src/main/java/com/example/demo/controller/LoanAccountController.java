package com.example.demo.controller;

import com.example.demo.entities.LoanAccount;
import com.example.demo.repositories.LoanAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-accounts")
@CrossOrigin(origins = "*")
public class LoanAccountController {

    @Autowired
    private LoanAccountRepository loanAccountRepository;

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<?> getLoanAccountByApplicationId(@PathVariable Long applicationId) {
        LoanAccount account = loanAccountRepository.findByApplicationId(applicationId);
        if (account == null) {
            return ResponseEntity
                    .status(404)
                    .body("Loan account not found for applicationId: " + applicationId);
        }
        return ResponseEntity.ok(account);
    }
}
