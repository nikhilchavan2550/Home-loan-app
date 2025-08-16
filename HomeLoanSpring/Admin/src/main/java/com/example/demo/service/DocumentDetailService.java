package com.example.demo.service;

import com.example.demo.client.DocumentDetailClient;
import com.example.demo.vo.DocumentDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentDetailService {

    @Autowired
    private DocumentDetailClient documentDetailClient;

    public List<DocumentDetailVO> getDocumentByPersonalId(long personalId) {
        return documentDetailClient.getDocumentByPersonalId(personalId);
    }
}
