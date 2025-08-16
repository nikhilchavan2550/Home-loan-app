package com.example.demo.vo;

import java.util.Date;

public class DocumentDetailVO {

    private int id;
    private int personalId;
    private String panCard;
    private String voterId;
    private String salarySlip;
    private String loa;                // Letter of Authorization
    private String nocBuilder;        // No Objection Certificate
    private String agreementToSale;
    private Date uploadedDate;

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

    public String getPanCard() {
        return panCard;
    }
    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public String getVoterId() {
        return voterId;
    }
    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getSalarySlip() {
        return salarySlip;
    }
    public void setSalarySlip(String salarySlip) {
        this.salarySlip = salarySlip;
    }

    public String getLoa() {
        return loa;
    }
    public void setLoa(String loa) {
        this.loa = loa;
    }

    public String getNocBuilder() {
        return nocBuilder;
    }
    public void setNocBuilder(String nocBuilder) {
        this.nocBuilder = nocBuilder;
    }

    public String getAgreementToSale() {
        return agreementToSale;
    }
    public void setAgreementToSale(String agreementToSale) {
        this.agreementToSale = agreementToSale;
    }

    public Date getUploadedDate() {
        return uploadedDate;
    }
    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }
}
