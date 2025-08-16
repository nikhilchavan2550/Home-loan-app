package com.example.orlobank.repositories;

import com.example.orlobank.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    // custId is the primary key
}
