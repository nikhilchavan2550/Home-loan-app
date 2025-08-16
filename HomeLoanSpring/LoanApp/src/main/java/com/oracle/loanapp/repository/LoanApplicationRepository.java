package com.oracle.loanapp.repository;

import com.oracle.loanapp.entity.LoanApplication;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
	List<LoanApplication> findByCustIdOrderByIdDesc(Long custId);

}
