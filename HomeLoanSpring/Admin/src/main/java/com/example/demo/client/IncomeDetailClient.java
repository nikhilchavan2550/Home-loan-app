package com.example.demo.client;

import com.example.demo.vo.IncomeDetailVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
    name = "income-service",
    url = "http://localhost:8080" // Hardcoded friend’s service URL
)
public interface IncomeDetailClient { //localhost:8083/api/income/1


    // Get income detail by ID
    @GetMapping("/api/income/{id}")
    IncomeDetailVO getIncomeDetailById(@PathVariable("id") long id);
}
