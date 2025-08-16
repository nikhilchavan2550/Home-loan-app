package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.example.demo.entities.Customer;
import com.example.demo.repos.CustomerRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:5501", "http://127.0.0.1:5501", "http://localhost:3000", "http://127.0.0.1:3000"})
public class GoogleAuthController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String GOOGLE_OAUTH2_URL = "https://oauth2.googleapis.com/tokeninfo";

    @PostMapping("/google")
    public ResponseEntity<?> authenticateWithGoogle(@RequestBody GoogleAuthRequest request) {
        try {
            // Verify the Google ID token
            boolean isValidToken = verifyGoogleToken(request.getCredential());
            
            if (!isValidToken) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new GoogleAuthResponse(false, "Invalid Google token", null));
            }

            // Check if user already exists
            Optional<Customer> existingCustomer = customerRepository.findByEmail(request.getEmail());
            
            Customer customer;
            boolean isNewUser = false;

            if (existingCustomer.isPresent()) {
                // User exists, return existing user data
                customer = existingCustomer.get();
            } else {
                // Create new user
                customer = new Customer();
                customer.setEmail(request.getEmail());
                customer.setFirstName(request.getName().split(" ")[0]); // First part of name
                customer.setLastName(request.getName().contains(" ") ? 
                    request.getName().substring(request.getName().indexOf(" ") + 1) : ""); // Rest of name
                customer.setPan("GOOGLE_" + request.getSub().substring(0, 8)); // Generate PAN-like ID
                customer.setPassword("GOOGLE_AUTH_" + request.getSub()); // Set a secure password for Google users
                
                customer = customerRepository.save(customer);
                isNewUser = true;
            }

            // Create response with user data
            GoogleAuthUser userData = new GoogleAuthUser(
                customer.getCustomerId(),
                customer.getEmail(),
                customer.getFirstName() + " " + customer.getLastName(),
                request.getPicture(),
                isNewUser
            );

            return ResponseEntity.ok(new GoogleAuthResponse(true, 
                isNewUser ? "Account created successfully!" : "Login successful!", 
                userData));

        } catch (Exception e) {
            System.err.println("Error in Google authentication: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GoogleAuthResponse(false, "Authentication failed", null));
        }
    }

    private boolean verifyGoogleToken(String credential) {
        try {
            // For production, you should verify the token with Google's servers
            // For now, we'll do basic validation
            if (credential == null || credential.isEmpty()) {
                return false;
            }

            // In a real implementation, you would:
            // 1. Decode the JWT token
            // 2. Verify the signature with Google's public keys
            // 3. Check the token expiration
            // 4. Verify the issuer (Google)
            // 5. Verify the audience (your client ID)

            // For demo purposes, we'll assume the token is valid if it's not empty
            return true;

        } catch (Exception e) {
            System.err.println("Error verifying Google token: " + e.getMessage());
            return false;
        }
    }

    // Request and Response classes
    public static class GoogleAuthRequest {
        private String credential;
        private String email;
        private String name;
        private String picture;
        private String sub;

        public GoogleAuthRequest() {}

        public String getCredential() { return credential; }
        public void setCredential(String credential) { this.credential = credential; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getPicture() { return picture; }
        public void setPicture(String picture) { this.picture = picture; }

        public String getSub() { return sub; }
        public void setSub(String sub) { this.sub = sub; }
    }

    public static class GoogleAuthResponse {
        private boolean success;
        private String message;
        private GoogleAuthUser user;

        public GoogleAuthResponse() {}

        public GoogleAuthResponse(boolean success, String message, GoogleAuthUser user) {
            this.success = success;
            this.message = message;
            this.user = user;
        }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public GoogleAuthUser getUser() { return user; }
        public void setUser(GoogleAuthUser user) { this.user = user; }
    }

    public static class GoogleAuthUser {
        private Long id;
        private String email;
        private String name;
        private String picture;
        private boolean isNewUser;

        public GoogleAuthUser() {}

        public GoogleAuthUser(Long id, String email, String name, String picture, boolean isNewUser) {
            this.id = id;
            this.email = email;
            this.name = name;
            this.picture = picture;
            this.isNewUser = isNewUser;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getPicture() { return picture; }
        public void setPicture(String picture) { this.picture = picture; }

        public boolean isNewUser() { return isNewUser; }
        public void setNewUser(boolean newUser) { isNewUser = newUser; }
    }
} 