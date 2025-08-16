package com.example.demo.service;

import com.example.demo.entities.AdminDetails;
import com.example.demo.entities.Customer;
import com.example.demo.repos.AdminDetailsRepository;
import com.example.demo.repos.CustomerRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.security.MyUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class ApplicantService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private AdminDetailsRepository adminRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    // ======================= CUSTOMER =======================

    public Map<String, Object> registerCustomer(Customer customer) {
        Map<String, Object> response = new HashMap<>();

        if (customerRepo.findByEmail(customer.getEmail()).isPresent()) {
            response.put("message", "Email already exists.");
            return response;
        }

        if (customerRepo.findByPan(customer.getPan()).isPresent()) {
            response.put("message", "PAN already exists.");
            return response;
        }

        // Hash the password
        String hashedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(hashedPassword);

        Customer savedCustomer = customerRepo.save(customer);
        response.put("message", "Customer registered successfully.");
        response.put("customerId", savedCustomer.getCustomerId());

        return response;
    }



    public Map<String, Object> loginCustomer(String email, String password) {
        Map<String, Object> response = new HashMap<>();

        Optional<Customer> optionalCustomer = customerRepo.findByEmail(email);
        if (optionalCustomer.isEmpty()) {
            response.put("message", "Customer not found.");
            return response;
        }

        Customer customer = optionalCustomer.get();

        if (!passwordEncoder.matches(password, customer.getPassword())) {
            response.put("message", "Incorrect password.");
            return response;
        }

        // Authenticate user (optional but recommended for full Spring Security flow)
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );

        // Generate JWT token
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtUtil.generateToken(userDetails);

        // Return response
        response.put("message", "Login successful.");
        response.put("customerId", customer.getCustomerId());
        response.put("token", token);
        return response;
    }


    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepo.findById(id).orElse(null);
    }

    public String patchCustomer(Long id, Map<String, Object> updates) {
        Optional<Customer> optionalCustomer = customerRepo.findById(id);
        if (optionalCustomer.isEmpty()) {
            return "Customer not found.";
        }

        Customer customer = optionalCustomer.get();
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String fieldName = entry.getKey();
            Object newValue = entry.getValue();
            try {
                Field field = Customer.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(customer, newValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                return "Invalid field: " + fieldName;
            }
        }

        customerRepo.save(customer);
        return "Customer updated successfully.";
    }

    public String deleteCustomer(Long id) {
        if (customerRepo.existsById(id)) {
            customerRepo.deleteById(id);
            return "Customer deleted successfully!";
        } else {
            return "Customer not found with ID: " + id;
        }
    }

    public String resetCustomerPassword(String email, String pan, String newPassword) {
        Optional<Customer> optionalCustomer = customerRepo.findByEmail(email);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            if (customer.getPan().equalsIgnoreCase(pan)) {
                customer.setPassword(passwordEncoder.encode(newPassword));
                customerRepo.save(customer);
                return "Password reset successful.";
            } else {
                return "PAN does not match the email.";
            }
        } else {
            return "Customer not found.";
        }
    }


    // ======================= ADMIN =======================

    public Map<String, Object> loginAdmin(String email, String password) {
        Map<String, Object> response = new HashMap<>();

        Optional<AdminDetails> optionalAdmin = adminRepo.findByEmail(email);
        if (optionalAdmin.isEmpty()) {
            response.put("message", "Admin not found.");
            return response;
        }

        AdminDetails admin = optionalAdmin.get();

        // ✅ Use PasswordEncoder to match hashed password
        if (!admin.getPassword().equals(password)) {
            response.put("message", "Incorrect password.");
            return response;
        }

        // ✅ Authenticate using Spring Security (can throw exception if credentials are invalid)
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );

        // ✅ Load UserDetails and generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtUtil.generateToken(userDetails);

        // ✅ Return response with token and admin details
        response.put("message", "Login successful.");
        response.put("token", token);
        response.put("adminId", admin.getAdminId());
        response.put("email", admin.getEmail());
        response.put("firstName", admin.getFirstName());
        response.put("lastName", admin.getLastName());

        return response;
    }


    
    
  


    public Customer getCustomerByEmail(String email) {
    	Customer cust = new Customer();
    	Optional<Customer> optionalCustomer = customerRepo.findByEmail(email);
    	 if (optionalCustomer.isPresent()) {
    		 cust = optionalCustomer.get();
    		 return cust;
    	 }
    	 
    	 return cust;
        
    }


}
