package com.oracle.loanapp.dto.response;

import java.time.LocalDate;

public class PropertyDetailsResponseDto {
    private Long id;
    private Long personalId;
    private String propertyName;
    private String propertyLocation;
    private String city;
    private String state;
    private String propertyArea;
    private Double estimatedAmount;
    private LocalDate constructionCompletionDate;
    private LocalDate createdDate;

    // Getters
    public Long getId() { return id; }
    public Long getPersonalId() { return personalId; }
    public String getPropertyName() { return propertyName; }
    public String getPropertyLocation() { return propertyLocation; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPropertyArea() { return propertyArea; }
    public Double getEstimatedAmount() { return estimatedAmount; }
    public LocalDate getConstructionCompletionDate() { return constructionCompletionDate; }
    public LocalDate getCreatedDate() { return createdDate; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setPersonalId(Long personalId) { this.personalId = personalId; }
    public void setPropertyName(String propertyName) { this.propertyName = propertyName; }
    public void setPropertyLocation(String propertyLocation) { this.propertyLocation = propertyLocation; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public void setPropertyArea(String propertyArea) { this.propertyArea = propertyArea; }
    public void setEstimatedAmount(Double estimatedAmount) { this.estimatedAmount = estimatedAmount; }
    public void setConstructionCompletionDate(LocalDate date) { this.constructionCompletionDate = date; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }
}
