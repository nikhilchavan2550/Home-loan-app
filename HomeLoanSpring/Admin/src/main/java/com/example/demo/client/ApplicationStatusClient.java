// ApplicationStatusClient.java
package com.example.demo.client;

import com.example.demo.dto.UpdateStatusRequest;
import com.example.demo.vo.ApplicationStatusVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "application-status-client", url = "http://localhost:8086") // Change port if needed
public interface ApplicationStatusClient {

    @PostMapping("/api/application-status/update")
    ApplicationStatusVO updateStatus(@RequestBody UpdateStatusRequest request);
}
