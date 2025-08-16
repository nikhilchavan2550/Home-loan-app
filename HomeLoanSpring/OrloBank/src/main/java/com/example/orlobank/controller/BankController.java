package com.example.orlobank.controller;

import com.example.orlobank.entities.BankAccount;
import com.example.orlobank.entities.Customer;
import com.example.orlobank.entities.Transaction;
import com.example.orlobank.repositories.CustomerRepository;
import com.example.orlobank.service.BankService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/bank")
@CrossOrigin(origins = "*")

public class BankController {
	
	@Autowired
    private BankService bankService;
    
	@Autowired
    private CustomerRepository customerRepo;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

	@PostMapping("/create")
	public ResponseEntity<BankAccount> createAccount(@RequestBody Map<String, String> body) {
	    String custId = body.get("custId");

	    String timestamp = String.valueOf(System.currentTimeMillis());
	    String accountNumber = Integer.toHexString(timestamp.hashCode()).toUpperCase();

	    BigDecimal initialAmount = BigDecimal.ZERO;

	    BankAccount account = bankService.createAccount(custId, accountNumber, initialAmount);
	    return ResponseEntity.ok(account);
	}

    
	@PostMapping("/create-customer")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
	    Customer savedCustomer = bankService.createCustomerWithAccount(customer);
	    return ResponseEntity.ok(savedCustomer);
	}



    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@RequestBody Map<String, String> body) {
        String accNum = body.get("accountNumber");
        BigDecimal amount = new BigDecimal(body.get("amount"));
        return ResponseEntity.ok(bankService.deposit(accNum, amount));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestBody Map<String, String> body) {
        String accNum = body.get("accountNumber");
        BigDecimal amount = new BigDecimal(body.get("amount"));
        return ResponseEntity.ok(bankService.withdraw(accNum, amount));
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody Map<String, String> body) {
        String from = body.get("fromAccount");
        String to = body.get("toAccount");
        BigDecimal amount = new BigDecimal(body.get("amount"));
        bankService.transfer(from, to, amount);
        return ResponseEntity.ok("Transfer successful");
    }
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String custId = body.get("custId");
        String password = body.get("password");

        Optional<Customer> customerOpt = customerRepo.findById(custId);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (passwordEncoder.matches(password, customer.getPassword())) {
                return ResponseEntity.ok("Login successful");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable String accountNumber) {
        return ResponseEntity.ok(bankService.getBalance(accountNumber));
    }
    



    


}
