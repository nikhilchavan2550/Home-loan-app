package com.example.orlobank.repositories;

import com.example.orlobank.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByAccountNumber(String accountNumber);
    
    List<BankAccount> findByCustomer_CustId(String custId);

}
