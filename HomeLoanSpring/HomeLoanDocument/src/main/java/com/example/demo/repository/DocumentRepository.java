package com.example.demo.repository;

import com.example.demo.entity.Document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByCustid(Long custid);

}
