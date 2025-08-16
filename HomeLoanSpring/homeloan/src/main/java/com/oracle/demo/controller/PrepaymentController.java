package com.oracle.demo.controller;

import com.oracle.demo.service.LoanClosureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan")
@CrossOrigin(origins = "*")
public class PrepaymentController {

    @Autowired
    private LoanClosureService loanClosureService;

//    @PostMapping("/prepay/{accountNumber}")
//    public ResponseEntity<String> prepayLoan(@PathVariable String accountNumber,
//                                             @RequestParam double amount) {
//        String result = loanClosureService.prepayLoan(accountNumber, amount);
//        return ResponseEntity.ok(result);
//    }
}
