package com.example.demo.client;

import com.example.demo.vo.DocumentDetailVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "document-service", url = "http://localhost:8080/api/document")
public interface DocumentDetailClient {

    @GetMapping("/personal/{personalId}")
    List<DocumentDetailVO> getDocumentByPersonalId(@PathVariable("personalId") long personalId);
}
