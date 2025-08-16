package com.example.demo.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findByEmail(String email);   // For login
    boolean existsByEmail(String email);            // For email uniqueness check
    boolean existsByPan(String pan);   // For PAN uniqueness check
    Optional<Customer> findByPan(String pan);

}
