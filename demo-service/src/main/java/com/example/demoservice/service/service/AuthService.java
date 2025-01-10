package com.example.demoservice.service.service;

import com.example.demoservice.entity.UserBase;
import com.example.demoservice.repository.IUserBaseRepository;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Resource
    private  IUserBaseRepository userBaseRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    public UserBase authenticate(String account, String password) {
        Optional<UserBase> userOptional = userBaseRepository.findByAccount(account);

        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            return userOptional.get();
        }
        return null;

    }
}
