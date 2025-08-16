package com.example.demo.controller;

import com.example.demo.service.IncomeDetailService;
import com.example.demo.vo.IncomeDetailVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501") 
@RequestMapping("/api/income")
public class IncomeDetailController {

    private final IncomeDetailService service;

    public IncomeDetailController(IncomeDetailService service) {
        this.service = service;
    }

   

    // Get income detail by ID
    @GetMapping("/{id}")
    public IncomeDetailVO getIncomeById(@PathVariable long id) {
        return service.getIncomeDetailById(id);
    }
}
