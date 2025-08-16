// package: com.example.demo.controller

package com.example.demo.controller;

import com.example.demo.dto.UpdateStatusRequest;
import com.example.demo.service.ApplicationStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/status")
public class ApplicationStatusController {

    @Autowired
    private ApplicationStatusService statusService;

    @PostMapping("/update")
    public String updateStatus(@RequestBody UpdateStatusRequest request) {
        statusService.callUpdateStatus(
                request.getApplicationId(),
                request.getNewStatus(),
                request.getAdminId()
        );
        return "Status updated successfully to: " + request.getNewStatus();
    }
}
