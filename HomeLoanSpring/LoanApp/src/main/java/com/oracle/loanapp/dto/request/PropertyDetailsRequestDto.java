package com.oracle.loanapp.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PropertyDetailsRequestDto {

    @NotNull(message = "personalId is required")
    private Long personalId;

    @NotNull(message = "propertyName is required")
    @Size(max = 100)
    private String propertyName;

    @NotNull(message = "propertyLocation is required")
    @Size(max = 100)
    private String propertyLocation;

    @NotNull(message = "city is required")
    @Size(max = 50)
    private String city;

    @NotNull(message = "state is required")
    @Size(max = 50)
    private String state;

    @NotNull(message = "propertyArea is required")
    @Size(max = 30)
    private String propertyArea;

    @NotNull(message = "estimatedAmount is required")
    private BigDecimal estimatedAmount;

    @NotNull(message = "constructionCompletionDate is required")
    private LocalDate constructionCompletionDate;

    private LocalDate createdDate;

    // Getters and Setters
    public Long getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Long personalId) {
        this.personalId = personalId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyLocation() {
        return propertyLocation;
    }

    public void setPropertyLocation(String propertyLocation) {
        this.propertyLocation = propertyLocation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPropertyArea() {
        return propertyArea;
    }

    public void setPropertyArea(String propertyArea) {
        this.propertyArea = propertyArea;
    }

    public BigDecimal getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(BigDecimal estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public LocalDate getConstructionCompletionDate() {
        return constructionCompletionDate;
    }

    public void setConstructionCompletionDate(LocalDate constructionCompletionDate) {
        this.constructionCompletionDate = constructionCompletionDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = LocalDate.now();
    }
}
