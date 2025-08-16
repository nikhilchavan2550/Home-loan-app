package com.example.demo.client;

import com.example.demo.vo.PersonalDetailVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "personal-detail-client", url = "http://localhost:8080/api/personal")
public interface PersonalDetailClient {

    @GetMapping("/id/{id}")
    PersonalDetailVO getPersonalDetailById(@PathVariable("id") long id);
}
