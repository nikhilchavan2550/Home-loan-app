package com.oracle.loanapp.repository;

import com.oracle.loanapp.entity.IncomeDetails;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeDetailsRepository extends JpaRepository<IncomeDetails, Long> {
	Optional<IncomeDetails> findByPersonalDetailsId(Long personalId);

}
