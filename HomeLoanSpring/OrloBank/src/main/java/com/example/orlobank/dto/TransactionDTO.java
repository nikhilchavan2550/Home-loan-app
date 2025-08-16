package com.example.orlobank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private String type;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String description;
    private String targetAccountNumber;

    public TransactionDTO(Long id, String type, BigDecimal amount, LocalDateTime timestamp, String description, String targetAccountNumber) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
        this.targetAccountNumber = targetAccountNumber;
    }

    public Long getId() { return id; }
    public String getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getDescription() { return description; }
    public String getTargetAccountNumber() { return targetAccountNumber; }
}
