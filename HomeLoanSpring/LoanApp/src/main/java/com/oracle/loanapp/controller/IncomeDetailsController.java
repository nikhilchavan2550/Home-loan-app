package com.oracle.loanapp.controller;

import com.oracle.loanapp.dto.request.IncomeDetailsRequestDto;

import com.oracle.loanapp.dto.response.IncomeDetailsResponseDto;
import com.oracle.loanapp.service.IncomeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/income")
@CrossOrigin(origins = "*")
public class IncomeDetailsController {
    @Autowired
    private IncomeDetailsService incomeDetailsService;

    @PostMapping("/add")
    public ResponseEntity<IncomeDetailsResponseDto> addIncome(@Valid @RequestBody IncomeDetailsRequestDto requestDto) {
        return ResponseEntity.ok(incomeDetailsService.addIncomeDetails(requestDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<IncomeDetailsResponseDto>> getAllIncomes() {
        return ResponseEntity.ok(incomeDetailsService.getAllIncomeDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeDetailsResponseDto> getIncomeById(@PathVariable Long id) {
        return ResponseEntity.ok(incomeDetailsService.getIncomeDetailsById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<IncomeDetailsResponseDto> updateIncome(@PathVariable Long id, @Valid @RequestBody IncomeDetailsRequestDto requestDto) {
        return ResponseEntity.ok(incomeDetailsService.updateIncomeDetails(id, requestDto));
    }
    
    @GetMapping("/byPersonalId/{personalId}")
    public ResponseEntity<?> getIncomeByPersonalId(@PathVariable Long personalId) {
        IncomeDetailsResponseDto incomeDetails = incomeDetailsService.getIncomeDetailsByPersonalId(personalId);

        if (incomeDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No income details found for personal ID: " + personalId));
        }

        return ResponseEntity.ok(incomeDetails);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        incomeDetailsService.deleteIncomeDetails(id);
        return ResponseEntity.noContent().build();
    }
}
