package com.example.demoservice.service.service;


import com.example.demoservice.entity.log.LoginLogEntity;
import com.example.demoservice.model.LogMessageQueueModel;
import com.example.demoservice.repository.ILoginLogRepository;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class LogService {
    @Resource
    ILoginLogRepository loginLogRepository;

    public void saveLoginLog(LogMessageQueueModel model) {
        System.out.println("saveLoginLog " + "save");
        //loginLogRepository.save(new LoginLogEntity(model));
    }
}
