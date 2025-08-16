package com.example.orlobank.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "transactions")
public class Transaction {

    public enum Type {
        DEPOSIT, WITHDRAWAL, TRANSFER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private String description;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private BankAccount account;

    // Optional: for transfers
    private String targetAccountNumber;

    // Constructors
    public Transaction() {}

    public Transaction(Type type, BigDecimal amount, LocalDateTime timestamp, BankAccount account, String description, String targetAccountNumber) {
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.account = account;
        this.description = description;
        this.targetAccountNumber = targetAccountNumber;
    }

	public Long getId() {
		return id;
	}

	public Type getType() {
		return type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getDescription() {
		return description;
	}

	public BankAccount getAccount() {
		return account;
	}

	public String getTargetAccountNumber() {
		return targetAccountNumber;
	}


    
    
}
