package com.oracle.loanapp.dto.request;

import java.time.LocalDate;
import jakarta.validation.constraints.*;

public class PersonalDetailsRequestDto {
	
    @NotNull(message = "Id is required")
	private Long id;

    @NotNull(message = "First name is required")
    private String firstName;

    private String middleName;

    @NotNull(message = "Last name is required")
    private String lastName;

    private LocalDate dob;
    private String gender;

    @NotNull(message = "Aadhar number is required")
    @Size(min = 12, max = 12)
    private String aadharNo;

//    @NotNull(message = "PAN number is required")
//    @Size(min = 10, max = 10)
    private String panNo;

    @NotNull(message = "Phone number is required")
    private String phoneNumber;

    private String nationality;
    private String email;
    private String address;

    // --- Getters ---
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public LocalDate getDob() { return dob; }
    public String getGender() { return gender; }
    public String getAadharNo() { return aadharNo; }
    public String getPanNo() { return panNo; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getNationality() { return nationality; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	// --- Setters ---
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public void setGender(String gender) { this.gender = gender; }
    public void setAadharNo(String aadharNo) { this.aadharNo = aadharNo; }
    public void setPanNo(String panNo) { this.panNo = panNo; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
}
