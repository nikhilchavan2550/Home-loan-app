package com.example.demo.controller;

import com.example.demo.entities.Customer;
//import com.example.demo.repos.AdminDetailsRepository;
import com.example.demo.service.ApplicantService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/applicant")
@CrossOrigin(origins = {"http://localhost:5501", "http://127.0.0.1:5501", "http://localhost:3000", "http://127.0.0.1:3000"})
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;
    
    //@Autowired
    //private AdminDetailsRepository adminRepo;

    // ========================= CUSTOMER =========================

    @PostMapping("/customer/register")
    public ResponseEntity<Map<String, Object>> registerCustomer(@Valid @RequestBody Customer customer) {
        Map<String, Object> result = applicantService.registerCustomer(customer);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/customer/login")
    public ResponseEntity<Map<String, Object>> customerLogin(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        Map<String, Object> result = applicantService.loginCustomer(email, password);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/customer/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(applicantService.getAllCustomers());
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        Customer customer = applicantService.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.status(404).body("Customer not found with ID: " + id);
        }
    }

    @PatchMapping("/customer/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        String result = applicantService.patchCustomer(id, updates);
        if (result.contains("successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        String result = applicantService.deleteCustomer(id);
        if (result.contains("successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(404).body(result);
        }
    }

    @PostMapping("/customer/forgot-password")
    public ResponseEntity<String> resetCustomerPassword(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String pan = data.get("pan");
        String newPassword = data.get("newPassword");

        String result = applicantService.resetCustomerPassword(email, pan, newPassword);
        return ResponseEntity.ok(result);
    }

    // ========================= ADMIN =========================

    @PostMapping("/admin/login")
    public ResponseEntity<Map<String, Object>> loginAdmin(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        Map<String, Object> response = applicantService.loginAdmin(email, password);

        String message = (String) response.get("message");

        if (message.contains("successful")) {
            return ResponseEntity.ok(response);
        } else if (message.contains("Incorrect")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else if (message.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Unexpected error"));
        }
    }

}
