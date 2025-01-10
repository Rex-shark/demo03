package com.example.demoservice.service.service;

import com.example.demoservice.repository.IUserBaseRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserBaseService {
    @Resource
    private IUserBaseRepository userBaseRepository;
}
