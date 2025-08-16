package com.example.demo.service;

import com.example.demo.client.PersonalDetailClient;
import com.example.demo.vo.PersonalDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalDetailService {

    @Autowired
    private PersonalDetailClient client;

    public PersonalDetailVO getPersonalDetailById(long id) {
        return client.getPersonalDetailById(id);
    }
}
