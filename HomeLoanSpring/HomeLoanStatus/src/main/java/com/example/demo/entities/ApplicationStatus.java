package com.example.demo.entities;

import jakarta.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class ApplicationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long applicationId; 
    
    

    private String currentStatus;
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ApplicationStatusHistory> statusHistory = new ArrayList<>();

    

	public ApplicationStatus(Long id, Long applicationId, String currentStatus, List<ApplicationStatusHistory> statusHistory) {
		super();
		this.id = id;
		this.applicationId = applicationId;
		this.currentStatus = currentStatus;
		this.statusHistory = statusHistory;
	}
	
	public ApplicationStatus()
	{
		
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getApplicationId() {
	    return applicationId;
	}

	public void setApplicationId(Long applicationId) {
	    this.applicationId = applicationId;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public List<ApplicationStatusHistory> getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(List<ApplicationStatusHistory> statusHistory) {
		this.statusHistory = statusHistory;
	}

    
}
