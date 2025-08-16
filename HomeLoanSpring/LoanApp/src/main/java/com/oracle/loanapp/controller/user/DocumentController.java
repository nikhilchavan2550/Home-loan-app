//package com.oracle.loanapp.controller.user;
//
//import com.oracle.loanapp.dto.request.DocumentUploadRequestDto;
//import com.oracle.loanapp.dto.response.DocumentResponseDto;
//import com.oracle.loanapp.service.DocumentService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/document")
//@Tag(name = "Document Upload", description = "Endpoints for document upload and retrieval")
//@CrossOrigin(origins = "*")
//public class DocumentController {
//
//    @Autowired
//    private DocumentService documentService;
//
//    @Operation(summary = "Upload a document")
//    @PostMapping("/upload")
//    public ResponseEntity<DocumentResponseDto> uploadDocument(@Valid @RequestBody DocumentUploadRequestDto requestDto) {
//        return ResponseEntity.ok(documentService.uploadDocument(requestDto));
//    }
//
//    @Operation(summary = "Get all documents")
//    @GetMapping("/all")
//    public ResponseEntity<List<DocumentResponseDto>> getAllDocuments() {
//        return ResponseEntity.ok(documentService.getAllDocuments());
//    }
//
//    @Operation(summary = "Get documents by personalId")
//    @GetMapping("/personal/{personalId}")
//    public ResponseEntity<List<DocumentResponseDto>> getDocumentsByPersonalId(@PathVariable Long personalId) {
//        return ResponseEntity.ok(documentService.getDocumentsByPersonalId(personalId));
//    }
//
//    @Operation(summary = "Update document by ID")
//    @PutMapping("/update/{id}")
//    public ResponseEntity<DocumentResponseDto> updateDocument(
//            @PathVariable Long id,
//            @Valid @RequestBody DocumentUploadRequestDto dto) {
//        return ResponseEntity.ok(documentService.updateDocument(id, dto));
//    }
//
//    @Operation(summary = "Delete document by ID")
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
//        documentService.deleteDocument(id);
//        return ResponseEntity.noContent().build();
//    }
//}
