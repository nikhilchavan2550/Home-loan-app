package com.oracle.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.oracle.demo.entities.EmiSchedule;
import com.oracle.demo.service.EmiScheduleService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emi")
@CrossOrigin(origins = "*")
public class EmiScheduleController {

    @Autowired
    private EmiScheduleService emiScheduleService;

    @GetMapping("/{accountNumber}")
    public List<EmiSchedule> getSchedule(@PathVariable String accountNumber) 
    {
        return emiScheduleService.getScheduleByAccountNumber(accountNumber);
    }

    @PostMapping("/generate")
    public String generateSchedule(@RequestParam String accountNumber,
                                   @RequestParam Double emiAmount,
                                   @RequestParam int durationInMonths) 
    {
        emiScheduleService.generateSchedule(accountNumber, emiAmount, durationInMonths);
        return "EMI schedule generated successfully!";
    }
    
    @PostMapping("/pay")
    public String payEmi(@RequestParam String accountNumber,@RequestParam Double amount) {
        return emiScheduleService.payEmi(accountNumber, amount);
    }
    
    @GetMapping("/paid/{accountNumber}")
    public List<EmiSchedule> getPaidEmis(@PathVariable String accountNumber) {
        return emiScheduleService.getPaidEmis(accountNumber);
    }

    @GetMapping("/unpaid/{accountNumber}")
    public List<EmiSchedule> getUnpaidEmis(@PathVariable String accountNumber) {
        return emiScheduleService.getUnpaidEmis(accountNumber);
    }
    
    @GetMapping("/summary/{accountNumber}")
    public ResponseEntity<Map<String, Object>> getEmiSummary(@PathVariable String accountNumber) {
        Map<String, Object> summary = emiScheduleService.getEmiPaymentSummary(accountNumber);
        return ResponseEntity.ok(summary);
    }

    @DeleteMapping("/{accountNumber}")
    public String deleteSchedule(@PathVariable String accountNumber) {
        emiScheduleService.deleteScheduleByAccountNumber(accountNumber);
        return "EMI schedule deleted successfully for account number: " + accountNumber;
    }

}
