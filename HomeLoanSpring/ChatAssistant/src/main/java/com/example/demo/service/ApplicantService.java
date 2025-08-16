package com.example.demo.service;

import com.example.demo.entities.AdminDetails;
import com.example.demo.entities.Customer;
import com.example.demo.repos.AdminDetailsRepository;
import com.example.demo.repos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class ApplicantService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private AdminDetailsRepository adminRepo;

    // ======================= CUSTOMER =======================

    public Map<String, Object> registerCustomer(Customer customer) {
        Map<String, Object> response = new HashMap<>();
        
        System.out.println("Registering customer: " + customer.getEmail() + " with PAN: " + customer.getPan());

        try {
            if (customerRepo.findByEmail(customer.getEmail()).isPresent()) {
                response.put("message", "Email already exists.");
                return response;
            }

            if (customerRepo.findByPan(customer.getPan()).isPresent()) {
                response.put("message", "PAN already exists.");
                return response;
            }

            Customer savedCustomer = customerRepo.save(customer);
            System.out.println("Customer saved successfully with ID: " + savedCustomer.getCustomerId());
            
            response.put("message", "Customer registered successfully.");
            response.put("customerId", savedCustomer.getCustomerId());

            return response;
        } catch (Exception e) {
            System.err.println("Error registering customer: " + e.getMessage());
            e.printStackTrace();
            response.put("message", "Registration failed: " + e.getMessage());
            return response;
        }
    }


    public Map<String, Object> loginCustomer(String email, String password) {
        Map<String, Object> response = new HashMap<>();
        System.out.println("Customer login attempt for email: " + email);
        
        try {
            Optional<Customer> optionalCustomer = customerRepo.findByEmail(email);

            if (optionalCustomer.isPresent()) {
                Customer customer = optionalCustomer.get();
                System.out.println("Customer found with ID: " + customer.getCustomerId());

                if (!customer.getPassword().equals(password)) {
                    response.put("message", "Incorrect password.");
                } else {
                    response.put("customerId", customer.getCustomerId());
                    response.put("message", "Login successful.");
                    System.out.println("Customer login successful for ID: " + customer.getCustomerId());
                }
            } else {
                response.put("message", "Customer not found.");
                System.out.println("Customer not found for email: " + email);
            }
        } catch (Exception e) {
            System.err.println("Error during customer login: " + e.getMessage());
            e.printStackTrace();
            response.put("message", "Login failed: " + e.getMessage());
        }

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
                customer.setPassword(newPassword);
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

        if (optionalAdmin.isPresent()) {
            AdminDetails admin = optionalAdmin.get();

            if (!admin.getPassword().equals(password)) {
                response.put("message", "Incorrect password.");
            } else {
                response.put("message", "Admin login successful. ID: " + admin.getAdminId());
            }
        } else {
            response.put("message", "Admin not found.");
        }

        return response;
    }

}
