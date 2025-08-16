package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enitity.AdminDetails;
import com.example.demo.repository.AdminDetailsRepository;

@Service
public class AdminService {

    @Autowired
    private AdminDetailsRepository adminRepo;

    // Static variables to store current logged-in admin info
    public static Long currentAdminId = null;
    public static String currentAdminName = null;

    public Map<String, Object> loginAdmin(String email, String password) {
        Map<String, Object> response = new HashMap<>();
        Optional<AdminDetails> optionalAdmin = adminRepo.findByEmail(email);

        if (optionalAdmin.isPresent()) {
            AdminDetails admin = optionalAdmin.get();

            if (!admin.getPassword().equals(password)) {
                response.put("message", "Incorrect password.");
            } else {
                response.put("message", "Admin login successful.");
                response.put("adminId", admin.getAdminId());
                response.put("adminName", admin.getFirstName() + " " + admin.getLastName());

                // ✅ Store current admin
                currentAdminId = admin.getAdminId();
                currentAdminName = admin.getFirstName() + " " + admin.getLastName();
            }
        } else {
            response.put("message", "Admin not found.");
        }

        return response;
    }
}
