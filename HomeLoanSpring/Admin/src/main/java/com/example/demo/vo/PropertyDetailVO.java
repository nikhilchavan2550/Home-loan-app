package com.example.demo.vo;

import java.util.Date;

public class PropertyDetailVO {

    private int id;
    private int personalId;
    private String propertyName;
    private String propertyLocation;
    private String city;
    private String state;
    private String propertyArea;                 // e.g., 1200 sqft
    private double estimatedAmount;
    private Date constructionCompletionDate;
    private Date createdDate;

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getPersonalId() {
        return personalId;
    }
    public void setPersonalId(int personalId) {
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

    public double getEstimatedAmount() {
        return estimatedAmount;
    }
    public void setEstimatedAmount(double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public Date getConstructionCompletionDate() {
        return constructionCompletionDate;
    }
    public void setConstructionCompletionDate(Date constructionCompletionDate) {
        this.constructionCompletionDate = constructionCompletionDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
