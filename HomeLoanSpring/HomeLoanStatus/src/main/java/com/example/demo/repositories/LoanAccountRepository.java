package com.example.demo.repositories;

import com.example.demo.entities.LoanAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanAccountRepository extends JpaRepository<LoanAccount, Long> {
    LoanAccount findByApplicationId(Long applicationId);
}
