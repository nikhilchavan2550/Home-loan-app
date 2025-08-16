package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IncomeDetailsResponseDto {
    private Long id;
    private Long personalId;
    private String employmentType;
    private Integer retirementAge;
    private String organizationType;
    private String employerName;
    private BigDecimal monthlyIncome;
    private LocalDate createdDate;

    public IncomeDetailsResponseDto() {}

    public IncomeDetailsResponseDto(Long id, Long personalId, String employmentType, Integer retirementAge, String organizationType, String employerName, BigDecimal monthlyIncome, LocalDate createdDate) {
        this.id = id;
        this.personalId = personalId;
        this.employmentType = employmentType;
        this.retirementAge = retirementAge;
        this.organizationType = organizationType;
        this.employerName = employerName;
        this.monthlyIncome = monthlyIncome;
        this.createdDate = LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPersonalId() { return personalId; }
    public void setPersonalId(Long personalId) { this.personalId = personalId; }

    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }

    public Integer getRetirementAge() { return retirementAge; }
    public void setRetirementAge(Integer retirementAge) { this.retirementAge = retirementAge; }

    public String getOrganizationType() { return organizationType; }
    public void setOrganizationType(String organizationType) { this.organizationType = organizationType; }

    public String getEmployerName() { return employerName; }
    public void setEmployerName(String employerName) { this.employerName = employerName; }

    public BigDecimal getMonthlyIncome() { return monthlyIncome; }
    public void setMonthlyIncome(BigDecimal monthlyIncome) { this.monthlyIncome = monthlyIncome; }

    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }
}
