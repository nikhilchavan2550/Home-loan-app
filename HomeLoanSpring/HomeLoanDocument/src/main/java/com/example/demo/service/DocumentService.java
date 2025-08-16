package com.example.demo.service;

import com.example.demo.entity.Document;
import com.example.demo.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document storeFile(MultipartFile file, String fileType, Long custid, String status) throws IOException {
        Path dir = Paths.get(uploadDir);
        if (!Files.exists(dir)) Files.createDirectories(dir);

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path target = dir.resolve(fileName);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        Document d = new Document(custid, status, fileName, fileType, target.toString(), LocalDateTime.now());
        return documentRepository.save(d);
    }

    public List<Document> getAll() {
        return documentRepository.findAll();
    }
    
    public List<Document> getByCustId(Long custid) {
        return documentRepository.findByCustid(custid);
    }


    public Document getById(Long id) {
        return documentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Not found id=" + id));
    }

    public Document update(Long id, Document updated) {
        Document existing = getById(id);
        existing.setCustid(updated.getCustid());
        existing.setStatus(updated.getStatus());
        existing.setFileName(updated.getFileName());
        existing.setFileType(updated.getFileType());
        existing.setFilePath(updated.getFilePath());
        existing.setUploadTime(updated.getUploadTime());
        return documentRepository.save(existing);
    }
    
    public Document updateStatus(Long id, String newStatus) {
        Document existing = documentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Document not found"));

        existing.setStatus(newStatus);
        return documentRepository.save(existing);
    }


    public void delete(Long id) {
        documentRepository.deleteById(id);
    }
}
