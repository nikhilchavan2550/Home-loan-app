package com.example.demo.service;

import com.example.demo.client.PropertyDetailClient;
import com.example.demo.vo.PropertyDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyDetailService {

    @Autowired
    private PropertyDetailClient client;

    public List<PropertyDetailVO> getAllPropertyDetails() {
        return client.getAllPropertyDetails();
    }

    public PropertyDetailVO getPropertyDetailById(long id) {
        return client.getPropertyDetailById(id);
    }
}
