package com.example.demo.client;

import com.example.demo.vo.PropertyDetailVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "property-detail-client", url = "http://localhost:8080/api/property")//http://localhost:8083/api/personal/1
public interface PropertyDetailClient {

    @GetMapping("/all")
    List<PropertyDetailVO> getAllPropertyDetails();

    @GetMapping("/{id}")
    PropertyDetailVO getPropertyDetailById(@PathVariable("id") long id);
}
