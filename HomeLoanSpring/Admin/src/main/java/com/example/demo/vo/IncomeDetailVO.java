package com.example.demo.vo;

import java.util.Date;

public class IncomeDetailVO {

    private int id;
    private int personalId;
    private String employmentType;        // Salaried / Self-employed
    private int retirementAge;
    private String organizationType;
    private String employerName;
    private double monthlyIncome;
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

    public String getEmploymentType() {
        return employmentType;
    }
    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public int getRetirementAge() {
        return retirementAge;
    }
    public void setRetirementAge(int retirementAge) {
        this.retirementAge = retirementAge;
    }

    public String getOrganizationType() {
        return organizationType;
    }
    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public String getEmployerName() {
        return employerName;
    }
    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }
    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
