package com.example.orlobank.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orlobank.entities.BankAccount;
import com.example.orlobank.entities.Transaction;
import com.example.orlobank.repositories.BankAccountRepository;
import com.example.orlobank.repositories.TransactionRepository;

@RestController
@RequestMapping("/api/transaction")
@CrossOrigin(origins = "*")
public class TransactionController {
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@GetMapping("/account/{accountId}")
	public ResponseEntity<List<Transaction>> getTransactionsByAccount(@PathVariable Long accountId) {
	    Optional<BankAccount> accountOpt = bankAccountRepository.findById(accountId);
	    if (accountOpt.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }
	    List<Transaction> transactions = transactionRepository.findByAccount(accountOpt.get());
	    return ResponseEntity.ok(transactions);
	}

	@GetMapping("/target/{accountNumber}")
	public ResponseEntity<List<Transaction>> getTransactionsByTarget(@PathVariable String accountNumber) {
	    List<Transaction> transactions = transactionRepository.findByTargetAccountNumber(accountNumber);
	    return ResponseEntity.ok(transactions);
	}


}
