package com.oracle.loanapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class DocumentUploadRequestDto {
    @NotNull(message = "personalId is required")
    private Long personalId;

    @NotNull(message = "documentType is required")
    private String documentType;

    @NotBlank(message = "documentName is required")
    private String documentName;

    @NotBlank(message = "documentUrl is required")
    private String documentUrl;

    @NotNull(message = "uploadedDate is required")
    private LocalDate uploadedDate;

    public DocumentUploadRequestDto() {}

    public DocumentUploadRequestDto(Long personalId, String documentType, String documentName, String documentUrl, LocalDate uploadedDate) {
        this.personalId = personalId;
        this.documentType = documentType;
        this.documentName = documentName;
        this.documentUrl = documentUrl;
        this.uploadedDate = uploadedDate;
    }

    public Long getPersonalId() { return personalId; }
    public void setPersonalId(Long personalId) { this.personalId = personalId; }

    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }

    public String getDocumentName() { return documentName; }
    public void setDocumentName(String documentName) { this.documentName = documentName; }

    public String getDocumentUrl() { return documentUrl; }
    public void setDocumentUrl(String documentUrl) { this.documentUrl = documentUrl; }

    public LocalDate getUploadedDate() { return uploadedDate; }
    public void setUploadedDate(LocalDate uploadedDate) { this.uploadedDate = uploadedDate; }
}
