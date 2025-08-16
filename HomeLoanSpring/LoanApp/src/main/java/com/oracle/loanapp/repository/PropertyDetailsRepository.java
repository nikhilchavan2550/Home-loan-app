package com.oracle.loanapp.repository;

import com.oracle.loanapp.entity.PropertyDetails;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyDetailsRepository extends JpaRepository<PropertyDetails, Long> {
	Optional<PropertyDetails> findByPersonalDetailsId(Long personalId);

}
