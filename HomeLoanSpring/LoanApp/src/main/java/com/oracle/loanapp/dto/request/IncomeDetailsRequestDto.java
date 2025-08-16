package com.oracle.loanapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;

public class IncomeDetailsRequestDto {
    @NotNull(message = "id is required")
	private Long id;

    @NotNull(message = "personalId is required")
    private Long personalId;

    @NotBlank(message = "employmentType is required")
    private String employmentType;

    @NotNull(message = "retirementAge is required")
    @Min(value = 18, message = "retirementAge must be at least 18")
    private Integer retirementAge;

    @NotBlank(message = "organizationType is required")
    private String organizationType;

    @NotBlank(message = "employerName is required")
    private String employerName;

    @NotNull(message = "monthlyIncome is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "monthlyIncome must be positive")
    private BigDecimal monthlyIncome;

    private LocalDate createdDate;

    // No-args constructor
    public IncomeDetailsRequestDto() {}

    // All-args constructor
    public IncomeDetailsRequestDto(Long id, Long personalId, String employmentType, Integer retirementAge,
                                   String organizationType, String employerName, BigDecimal monthlyIncome,
                                   LocalDate createdDate) {
    	this.id = id;
    	this.personalId = personalId;
        this.employmentType = employmentType;
        this.retirementAge = retirementAge;
        this.organizationType = organizationType;
        this.employerName = employerName;
        this.monthlyIncome = monthlyIncome;
        this.createdDate = LocalDate.now();
    }

    // Getters and Setters
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    
}
