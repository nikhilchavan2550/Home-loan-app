package com.oracle.demo.controller;

import com.oracle.demo.service.LoanClosureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/emi") // ✅ Matches frontend
@CrossOrigin(origins = "*")
public class ForeclosureController {

    @Autowired
    private LoanClosureService loanClosureService;

    @PostMapping("/foreclose")
    public ResponseEntity<String> forecloseLoan(@RequestBody Map<String, Object> request) {
        String accountNumber = request.get("accountNumber").toString();

        String result = loanClosureService.forecloseLoan(accountNumber);
        return ResponseEntity.ok(result);
    }
}
