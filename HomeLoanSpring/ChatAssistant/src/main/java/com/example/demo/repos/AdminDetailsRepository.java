package com.example.demo.repos;

import com.example.demo.entities.AdminDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminDetailsRepository extends JpaRepository<AdminDetails, Long> {

    Optional<AdminDetails> findByEmail(String email);
}
