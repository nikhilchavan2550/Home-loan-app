package com.example.demo.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class ApplicationStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    @JsonBackReference
    private ApplicationStatus application;

    private String status;

    private LocalDate changedAt;

    private Long changedByAdminId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ApplicationStatus getApplication() {
		return application;
	}

	public void setApplication(ApplicationStatus application) {
		this.application = application;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getChangedAt() {
		return changedAt;
	}

	public void setChangedAt(LocalDate changedAt) {
		this.changedAt = changedAt;
	}

	public Long getChangedByAdminId() {
		return changedByAdminId;
	}

	public void setChangedByAdminId(Long changedByAdminId) {
		this.changedByAdminId = changedByAdminId;
	}
}
