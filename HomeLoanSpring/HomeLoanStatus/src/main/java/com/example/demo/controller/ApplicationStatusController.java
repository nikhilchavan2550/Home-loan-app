package com.example.demo.controller;

import com.example.demo.dto.UpdateStatusRequest;
import com.example.demo.entities.ApplicationStatus;
import com.example.demo.services.ApplicationStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/application-status")
@CrossOrigin(origins = "*")
public class ApplicationStatusController {

    @Autowired
    private ApplicationStatusService statusService;

    @PostMapping("/submit")
    public ApplicationStatus submitNewApplication(@RequestParam Long applicationId) {
        
    	return statusService.createNewApplication(applicationId);
        
    }
    
    @PostMapping("/update")
    public ApplicationStatus updateStatus(@RequestBody UpdateStatusRequest request) {
        return statusService.updateStatus(
            request.getApplicationId(),
            request.getNewStatus(),
            request.getAdminId()
        );
    }
    
    @PostMapping("/revert/{applicationId}")
    public ApplicationStatus revertStatus(@PathVariable Long applicationId) {
        return statusService.revertLastStatus(applicationId);
    }


    @GetMapping("/{applicationId}")
    public Optional<ApplicationStatus> getStatus(@PathVariable Long applicationId) {
        return statusService.getStatusByApplicationId(applicationId);
    }
    
    
}
