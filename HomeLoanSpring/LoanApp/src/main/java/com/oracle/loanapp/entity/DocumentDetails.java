//package com.oracle.loanapp.entity;
//
//import jakarta.persistence.*;
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "document_details")
//@SequenceGenerator(name = "document_details_seq", sequenceName = "document_details_seq", allocationSize = 1)
//public class DocumentDetails {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_details_seq")
//    @Column(name = "id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "personal_id", referencedColumnName = "id", nullable = false)
//    private PersonalDetails personalDetails;
//
//    @Convert(converter = DocumentTypeConverter.class)
//    @Column(name = "document_type", nullable = false)
//    private DocumentType documentType;
//
//    @Column(name = "document_name", nullable = false)
//    private String documentName;
//
//    @Column(name = "document_url", nullable = false)
//    private String documentUrl;
//
//    @Column(name = "uploaded_date", nullable = false)
//    private LocalDate uploadedDate;
//
//    public DocumentDetails() {}
//
//    public DocumentDetails( PersonalDetails personalDetails, DocumentType documentType, String documentName, String documentUrl, LocalDate uploadedDate) {
//        this.personalDetails = personalDetails;
//        this.documentType = documentType;
//        this.documentName = documentName;
//        this.documentUrl = documentUrl;
//        this.uploadedDate = uploadedDate;
//    }
//
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public PersonalDetails getPersonalDetails() { return personalDetails; }
//    public void setPersonalDetails(PersonalDetails personalDetails) { this.personalDetails = personalDetails; }
//
//    public DocumentType getDocumentType() { return documentType; }
//    public void setDocumentType(DocumentType documentType) { this.documentType = documentType; }
//
//    public String getDocumentName() { return documentName; }
//    public void setDocumentName(String documentName) { this.documentName = documentName; }
//
//    public String getDocumentUrl() { return documentUrl; }
//    public void setDocumentUrl(String documentUrl) { this.documentUrl = documentUrl; }
//
//    public LocalDate getUploadedDate() { return uploadedDate; }
//    public void setUploadedDate(LocalDate uploadedDate) { this.uploadedDate = uploadedDate; }
//}
