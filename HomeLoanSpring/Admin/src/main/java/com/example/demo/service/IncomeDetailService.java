package com.example.demo.service;

import com.example.demo.client.IncomeDetailClient;
import com.example.demo.vo.IncomeDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeDetailService {

    @Autowired
    private IncomeDetailClient client;

    

    public IncomeDetailVO getIncomeDetailById(long id) {
        return client.getIncomeDetailById(id);
    }
}
