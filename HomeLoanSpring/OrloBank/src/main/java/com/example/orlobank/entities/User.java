package com.example.orlobank.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; 

    @Column(nullable = false)
    private String role; 

    @OneToOne
    @JoinColumn(name = "cust_id", referencedColumnName = "custId")
    private Customer customer;

    // Constructors
    public User() {}

    public User(String username, String password, String role, Customer customer) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.customer = customer;
    }

    // Getters and Setters
    // ...
}
