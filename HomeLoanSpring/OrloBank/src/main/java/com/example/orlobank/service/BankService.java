package com.example.orlobank.service;

import com.example.orlobank.entities.*;
import com.example.orlobank.repositories.*;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class BankService {

	@Autowired
    private final BankAccountRepository bankAccountRepo;
    
	@Autowired
    private final CustomerRepository customerRepo;
	
	@Autowired
    private final TransactionRepository transactionRepo;
    
	@Autowired
    private final PasswordEncoder passwordEncoder;  
    
    private final Random random = new Random();

	@Autowired
	private JavaMailSender mailSender;
	
    public BankService(
            BankAccountRepository bankAccountRepo,
            CustomerRepository customerRepo,
            TransactionRepository transactionRepo,
            PasswordEncoder passwordEncoder 
        ) {
            this.bankAccountRepo = bankAccountRepo;
            this.customerRepo = customerRepo;
            this.transactionRepo = transactionRepo;
            this.passwordEncoder = passwordEncoder;
        }

    public Customer createCustomerWithAccount(Customer customer) {
        if (customer.getCustId() == null || customer.getCustId().isEmpty()) {
            customer.setCustId(generateCustomerId());
        }

        String rawPassword = customer.getPassword();
        customer.setPassword(passwordEncoder.encode(rawPassword));

        Customer savedCustomer = customerRepo.save(customer);

        String accountNumber = generateAccountNumber();
        BankAccount account = new BankAccount(accountNumber, savedCustomer, BigDecimal.ZERO);
        bankAccountRepo.save(account);

        sendAccountDetailsEmail(savedCustomer.getEmail(), savedCustomer.getCustId(), accountNumber);

        return savedCustomer;
    }

    private void sendAccountDetailsEmail(String toEmail, String custId, String accountNumber) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Bank Account Details");
        message.setText("Dear Customer,\n\n" +
                "Your account has been successfully created.\n" +
                "Customer ID: " + custId + "\n" +
                "Account Number: " + accountNumber + "\n\n" +
                "Thank you for banking with us.\n" +
                "Orlo Bank");
        mailSender.send(message);
    }


    public BankAccount createAccount(String custId, String accountNumber, BigDecimal initialAmount) {
        Customer customer = customerRepo.findById(custId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        BankAccount account = new BankAccount(accountNumber, customer, initialAmount);
        return bankAccountRepo.save(account);
    }

    @Transactional
    public Transaction deposit(String accountNumber, BigDecimal amount) {
        BankAccount account = findAccount(accountNumber);
        account.setBalance(account.getBalance().add(amount));

        Transaction tx = new Transaction(Transaction.Type.DEPOSIT, amount, LocalDateTime.now(), account, "Deposit", null);
        transactionRepo.save(tx);

        return tx;
    }

    @Transactional
    public Transaction withdraw(String accountNumber, BigDecimal amount) {
        BankAccount account = findAccount(accountNumber);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));

        Transaction tx = new Transaction(Transaction.Type.WITHDRAWAL, amount, LocalDateTime.now(), account, "Withdrawal", null);
        transactionRepo.save(tx);

        return tx;
    }

    @Transactional
    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new RuntimeException("Cannot transfer to the same account");
        }

        BankAccount from = findAccount(fromAccountNumber);
        BankAccount to = findAccount(toAccountNumber);

        if (from.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        Transaction outTx = new Transaction(Transaction.Type.TRANSFER, amount, LocalDateTime.now(), from, "Transfer to " + toAccountNumber, toAccountNumber);
        Transaction inTx = new Transaction(Transaction.Type.DEPOSIT, amount, LocalDateTime.now(), to, "Received from " + fromAccountNumber, fromAccountNumber);

        transactionRepo.save(outTx);
        transactionRepo.save(inTx);
    }

    public BigDecimal getBalance(String accountNumber) {
        return findAccount(accountNumber).getBalance();
    }

    private BankAccount findAccount(String accountNumber) {
        return bankAccountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));
    }

    private String generateCustomerId() {
        return String.valueOf(1000 + random.nextInt(9000000));
    }

    private String generateAccountNumber() {
        long number = 1_000_000_000L + (long)(random.nextDouble() * 9_000_000_000L);
        return String.valueOf(number);
    }
    


}
