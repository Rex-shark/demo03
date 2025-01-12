package com.example.demoapi.service;

import com.example.demoapi.utils.JWTUtils;
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

    @Resource
    private JWTUtils jwtUtils;

    /**
     * 依照帳密 找user
     *
     * @param account 帳號
     * @param password 密碼(未加密)
     * @return UserBase
     */
    public UserBase authenticate(String account, String password) {
        //TODO 需判斷Status
        Optional<UserBase> userOptional = userBaseRepository.findByAccount(account);

        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            return userOptional.get();
        }
        return null;

    }

    public String generateJwtToken(UserBase userBase ,int minute) {
        return jwtUtils.generateToken(userBase,minute);
    }


}
