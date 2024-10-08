package com.example.demoservice.service.impl;

import com.example.demoservice.model.UserBase;
import com.example.demoservice.service.LoginService;
import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;
import com.example.demoservice.repository.IUserBaseRepository;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    IUserBaseRepository userBaseRepo;

    @Override
    public void test(String msg) {
        System.out.println("test run");
        Optional<UserBase> userBaseOpt = userBaseRepo.findByAccount(msg);
        if(userBaseOpt.isEmpty()){
            System.out.println("null");
        }else{
            UserBase userBase = userBaseOpt.get();
            System.out.println("userBase.getId() = " + userBase.getId());
        }
    }
}
