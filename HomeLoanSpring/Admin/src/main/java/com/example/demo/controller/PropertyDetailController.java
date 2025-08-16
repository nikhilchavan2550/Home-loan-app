package com.example.demo.controller;

import com.example.demo.service.PropertyDetailService;
import com.example.demo.vo.PropertyDetailVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501") 
@RequestMapping("/api/property")
public class PropertyDetailController {

    private final PropertyDetailService service;

    public PropertyDetailController(PropertyDetailService service) {
        this.service = service;
    }

    // Get all properties
    @GetMapping("/all")
    public List<PropertyDetailVO> getAllProperties() {
        return service.getAllPropertyDetails();
    }

    // Get property by ID
    @GetMapping("/{id}")
    public PropertyDetailVO getPropertyById(@PathVariable long id) {
        return service.getPropertyDetailById(id);
    }
}
