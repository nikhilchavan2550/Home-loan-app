package com.example.demo.service;

import com.example.demo.client.ApplicationStatusClient;
import com.example.demo.dto.UpdateStatusRequest;
import com.example.demo.vo.ApplicationStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationStatusService {

    @Autowired
    private ApplicationStatusClient statusClient;

    public ApplicationStatusVO callUpdateStatus(Long appId, String newStatus, Long adminId) {
        UpdateStatusRequest request = new UpdateStatusRequest();
        request.setApplicationId(appId);
        request.setNewStatus(newStatus);
        request.setAdminId(adminId);

        return statusClient.updateStatus(request);
    }
}
