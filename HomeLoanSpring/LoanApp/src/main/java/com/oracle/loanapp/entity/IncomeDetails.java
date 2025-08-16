package com.oracle.loanapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;


import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "INCOME_DETAILS")
@SequenceGenerator(name = "income_details_seq", sequenceName = "INCOME_DETAILS_SEQ", allocationSize = 1)
public class IncomeDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "income_details_seq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSONAL_ID", referencedColumnName = "ID", nullable = false)
    private PersonalDetails personalDetails;

    @Column(name = "EMPLOYMENT_TYPE", nullable = false)
    private String employmentType;

    @Column(name = "RETIREMENT_AGE", nullable = false)
    private Integer retirementAge;

    @Column(name = "ORGANIZATION_TYPE", nullable = false)
    private String organizationType;

    @Column(name = "EMPLOYER_NAME", nullable = false)
    private String employerName;

    @Column(name = "MONTHLY_INCOME", nullable = false)
    private BigDecimal monthlyIncome;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDate createdDate;

    public IncomeDetails() {}

    public IncomeDetails(Long id, PersonalDetails personalDetails, String employmentType, Integer retirementAge, String organizationType, String employerName, BigDecimal monthlyIncome, LocalDate createdDate) {
        this.id = id;
        this.personalDetails = personalDetails;
        this.employmentType = employmentType;
        this.retirementAge = retirementAge;
        this.organizationType = organizationType;
        this.employerName = employerName;
        this.monthlyIncome = monthlyIncome;
        this.createdDate = LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PersonalDetails getPersonalDetails() { return personalDetails; }
    public void setPersonalDetails(PersonalDetails personalDetails) { this.personalDetails = personalDetails; }

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
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = LocalDate.now(); }
}
