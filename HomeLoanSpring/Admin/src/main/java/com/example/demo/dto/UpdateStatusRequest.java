// UpdateStatusRequest.java
package com.example.demo.dto;

public class UpdateStatusRequest {
    private Long applicationId;
    private String newStatus;
    private Long adminId;

    // Getters & setters
    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public String getNewStatus() { return newStatus; }
    public void setNewStatus(String newStatus) { this.newStatus = newStatus; }

    public Long getAdminId() { return adminId; }
    public void setAdminId(Long adminId) { this.adminId = adminId; }
}
