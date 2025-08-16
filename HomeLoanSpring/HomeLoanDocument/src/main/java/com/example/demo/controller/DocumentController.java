package com.example.demo.controller;

import com.example.demo.dto.StatusUpdateRequest;
import com.example.demo.entity.Document;
import com.example.demo.service.DocumentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*")
public class DocumentController {

    private final DocumentService svc;
    public DocumentController(DocumentService svc) { this.svc = svc; }

    // 1. Upload
    @PostMapping("/upload")
    public Document upload(
        @RequestParam("file") MultipartFile file,
        @RequestParam("fileType") String fileType,
        @RequestParam("custid") Long custid,
        @RequestParam("status") String status
    ) throws IOException {
    	return svc.storeFile(file, fileType, custid, status);
    }

    // 2. List all
    @GetMapping("/all")
    public List<Document> all() {
        return svc.getAll();
    }

    // 3. Get by ID
    @GetMapping("/{id}")
    public Document byId(@PathVariable Long id) {
        return svc.getById(id);
    }

    // 4. Update metadata (you’d typically not re-upload file here)
//    @PutMapping("/update/{id}")
//    public Document update(
//        @PathVariable Long id,
//        @RequestBody Document payload
//    ) {
//        return svc.update(id, payload);
//    }
    
    @PutMapping("/update/{id}")
    public Document updateStatus(
        @PathVariable Long id,
        @RequestBody StatusUpdateRequest request
    ) {
        return svc.updateStatus(id, request.getStatus());
    }



    // 5. Delete
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        svc.delete(id);
    }

    // 6. Download
    @GetMapping("/download/{id}")
    public void download(@PathVariable Long id, HttpServletResponse resp) throws IOException {
        Document doc = svc.getById(id);
        File f = new File(doc.getFilePath());
        resp.setContentType(doc.getFileType());
        resp.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getFileName() + "\"");
        resp.setContentLengthLong(f.length());
        try (InputStream is = new FileInputStream(f);
             OutputStream os = resp.getOutputStream()) {
            is.transferTo(os);
        }
    }
    
 // 7. Get all documents for a given customer ID
    @GetMapping("/customer/{custid}")
    public List<Document> getByCustId(@PathVariable Long custid) {
        return svc.getByCustId(custid);
    }

}
