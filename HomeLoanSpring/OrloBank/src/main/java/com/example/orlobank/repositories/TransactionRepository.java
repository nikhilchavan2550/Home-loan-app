package com.example.orlobank.repositories;

import com.example.orlobank.entities.Transaction;
import com.example.orlobank.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findByAccount(BankAccount account);
	List<Transaction> findByTargetAccountNumber(String targetAccountNumber);


}
