package com.example.demo.controller;

import com.example.demo.service.DocumentDetailService;
import com.example.demo.vo.DocumentDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/document")
public class DocumentDetailController {

    @Autowired
    private DocumentDetailService documentDetailService;

    // Now returns a list
    @GetMapping("/personal/{personalId}")
    public List<DocumentDetailVO> getDocumentByPersonalId(@PathVariable long personalId) {
        return documentDetailService.getDocumentByPersonalId(personalId);
    }
}
