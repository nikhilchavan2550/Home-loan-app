package com.example.demo.controller;

import com.example.demo.service.PersonalDetailService;
import com.example.demo.vo.PersonalDetailVO;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501") 
@RequestMapping("/api/personal")
public class PersonalDetailController {

    private final PersonalDetailService service;

    public PersonalDetailController(PersonalDetailService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public PersonalDetailVO getPersonalById(@PathVariable long id) {
        return service.getPersonalDetailById(id);
    }
}
