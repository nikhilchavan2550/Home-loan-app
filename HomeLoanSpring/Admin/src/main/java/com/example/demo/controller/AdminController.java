package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.AdminService;

@RestController
//@CrossOrigin(origins = "http://127.0.0.1:5502") 

@CrossOrigin(
	    origins = {"http://127.0.0.1:5501", "http://127.0.0.1:5502"},
	    allowCredentials = "true"
	)

@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ✅ 1. Admin Login API
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginAdmin(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        Map<String, Object> response = adminService.loginAdmin(email, password);
        String message = (String) response.get("message");

        if (message.contains("successful")) {
            return ResponseEntity.ok(response);
        } else if (message.contains("Incorrect")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else if (message.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Unexpected error"));
        }
    }

    // ✅ 2. Get current logged-in admin ID and name
    @GetMapping("/current")
    public ResponseEntity<Map<String, Object>> getLoggedInAdmin() {
        if (AdminService.currentAdminId != null && AdminService.currentAdminName != null) {
            Map<String, Object> result = new HashMap<>();
            result.put("adminId", AdminService.currentAdminId);
            result.put("adminName", AdminService.currentAdminName);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "No admin is currently logged in."));
        }
    }

    // ✅ 3. Logout API – clears static login info
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logoutAdmin() {
        if (AdminService.currentAdminId != null || AdminService.currentAdminName != null) {
            AdminService.currentAdminId = null;
            AdminService.currentAdminName = null;
            return ResponseEntity.ok(Map.of("message", "Admin logged out successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "No admin was logged in."));
        }
    }
}
