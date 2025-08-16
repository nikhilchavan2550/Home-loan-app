package com.oracle.loanapp.dto.response;

import java.time.LocalDate;

public class PersonalDetailsResponseDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dob;
    private String gender;
    private String aadharNo;
    private String panNo;
    private String phoneNumber;
    private String nationality;
    private LocalDate createdDate;
    private String email;
    private String address;

    // ✅ Getters
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public LocalDate getDob() { return dob; }
    public String getGender() { return gender; }
    public String getAadharNo() { return aadharNo; }
    public String getPanNo() { return panNo; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getNationality() { return nationality; }
    public LocalDate getCreatedDate() { return createdDate; }
    
    

    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	// ✅ Setters
    public void setId(Long id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public void setGender(String gender) { this.gender = gender; }
    public void setAadharNo(String aadharNo) { this.aadharNo = aadharNo; }
    public void setPanNo(String panNo) { this.panNo = panNo; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }
}
