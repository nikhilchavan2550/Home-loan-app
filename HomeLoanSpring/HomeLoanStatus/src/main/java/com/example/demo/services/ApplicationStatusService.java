package com.example.demo.services;

import java.util.Optional;

import com.example.demo.entities.ApplicationStatus;

public interface ApplicationStatusService {

	ApplicationStatus createNewApplication(Long applicationId);

	ApplicationStatus updateStatus(Long applicationId, String newStatus, Long adminId);

	ApplicationStatus revertLastStatus(Long applicationId);

	Optional<ApplicationStatus> getStatusByApplicationId(Long applicationId);

}