//package com.oracle.loanapp.service.impl;
//
//import com.oracle.loanapp.dto.request.DocumentUploadRequestDto;
//import com.oracle.loanapp.dto.response.DocumentResponseDto;
//import com.oracle.loanapp.entity.DocumentDetails;
//import com.oracle.loanapp.entity.DocumentType;
//import com.oracle.loanapp.entity.PersonalDetails;
//import com.oracle.loanapp.repository.DocumentDetailsRepository;
//import com.oracle.loanapp.repository.PersonalDetailsRepository;
//import com.oracle.loanapp.service.DocumentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//public class DocumentServiceImpl implements DocumentService {
//
//    @Autowired
//    private DocumentDetailsRepository documentDetailsRepository;
//
//    @Autowired
//    private PersonalDetailsRepository personalDetailsRepository;
//
//    @Override
//    public DocumentResponseDto uploadDocument(DocumentUploadRequestDto requestDto) {
//        Optional<PersonalDetails> personalOpt = personalDetailsRepository.findById(requestDto.getPersonalId());
//        if (!personalOpt.isPresent()) {
//            throw new IllegalArgumentException("Invalid personalId: " + requestDto.getPersonalId());
//        }
//
//        DocumentDetails entity = new DocumentDetails();
//        entity.setPersonalDetails(personalOpt.get());
//
//        entity.setDocumentType(DocumentType.fromDisplayName(requestDto.getDocumentType()));
//
//        entity.setDocumentName(requestDto.getDocumentName());
//        entity.setDocumentUrl(requestDto.getDocumentUrl());
//        entity.setUploadedDate(requestDto.getUploadedDate());
//
//        DocumentDetails saved = documentDetailsRepository.save(entity);
//        return toResponseDto(saved);
//    }
//
//    @Override
//    public List<DocumentResponseDto> getAllDocuments() {
//        return documentDetailsRepository.findAll()
//                .stream()
//                .map(this::toResponseDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<DocumentResponseDto> getDocumentsByPersonalId(Long personalId) {
//        return documentDetailsRepository.findByPersonalDetailsId(personalId)
//                .stream()
//                .map(this::toResponseDto)
//                .collect(Collectors.toList());
//    }
//
//    private DocumentResponseDto toResponseDto(DocumentDetails entity) {
//        return new DocumentResponseDto(
//                entity.getId(),
//                entity.getPersonalDetails() != null ? entity.getPersonalDetails().getId() : null,
//                entity.getDocumentType() != null ? entity.getDocumentType().getDisplayName() : null,
//                entity.getDocumentName(),
//                entity.getDocumentUrl(),
//                entity.getUploadedDate()
//        );
//    }
//
//    @Override
//    public DocumentResponseDto updateDocument(Long id, DocumentUploadRequestDto dto) {
//        DocumentDetails entity = documentDetailsRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Document not found with ID: " + id));
//
//        PersonalDetails personal = personalDetailsRepository.findById(dto.getPersonalId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid personalId: " + dto.getPersonalId()));
//
//        entity.setPersonalDetails(personal);
//        entity.setDocumentType(DocumentType.fromDisplayName(dto.getDocumentType()));
//        entity.setDocumentName(dto.getDocumentName());
//        entity.setDocumentUrl(dto.getDocumentUrl());
//        entity.setUploadedDate(dto.getUploadedDate());
//
//        DocumentDetails updated = documentDetailsRepository.save(entity);
//        return toResponseDto(updated);
//    }
//
//    @Override
//    public void deleteDocument(Long id) {
//        if (!documentDetailsRepository.existsById(id)) {
//            throw new IllegalArgumentException("Document not found with ID: " + id);
//        }
//        documentDetailsRepository.deleteById(id);
//    }
//}
