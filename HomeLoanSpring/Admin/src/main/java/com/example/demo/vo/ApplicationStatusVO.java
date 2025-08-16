// ApplicationStatusVO.java
package com.example.demo.vo;

import java.time.LocalDateTime;

public class ApplicationStatusVO {
    private Long id;
    private Long applicationId;
    private String status;
    private LocalDateTime statusDate;
    private Long changedByAdmin;

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getStatusDate() { return statusDate; }
    public void setStatusDate(LocalDateTime statusDate) { this.statusDate = statusDate; }

    public Long getChangedByAdmin() { return changedByAdmin; }
    public void setChangedByAdmin(Long changedByAdmin) { this.changedByAdmin = changedByAdmin; }
}
