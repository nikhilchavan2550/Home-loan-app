package com.example.orlobank.controller;

import com.example.orlobank.entities.BankAccount;
import com.example.orlobank.entities.Customer;
import com.example.orlobank.repositories.BankAccountRepository;
import com.example.orlobank.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private BankAccountRepository bankAccountRepo;

    @GetMapping("/{custId}")
    public Customer getCustomer(@PathVariable String custId) {
        return customerRepo.findById(custId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @GetMapping("/{custId}/accounts")
    public List<BankAccount> getCustomerAccounts(@PathVariable String custId) {
        return bankAccountRepo.findByCustomer_CustId(custId);
    }
}
