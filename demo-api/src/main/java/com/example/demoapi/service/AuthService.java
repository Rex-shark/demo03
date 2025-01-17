package com.example.demoapi.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demoservice.utils.JWTUtils;
import com.example.demoservice.entity.UserBase;
import com.example.demoservice.repository.IUserBaseRepository;
import com.example.demoservice.utils.Utils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class AuthService {
    @Resource
    private  IUserBaseRepository userBaseRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JWTUtils jwtUtils;

    @Resource
    private Utils utils;

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


    public boolean validateTokenRefreshRequest(DecodedJWT jwt, HttpServletRequest request) throws NoSuchAlgorithmException {

        String jwtIp = jwt.getClaim("ip").asString();
        String jwtAgent = jwt.getClaim("agent").asString();

        String requestMD5Ip = utils.hashWithMD5(utils.getIpAddress(request));
        String requestMD5Agent = utils.hashWithMD5(request.getHeader("User-Agent"));

        return jwtIp != null && jwtIp.equals(requestMD5Ip) &&
                jwtAgent != null && jwtAgent.equals(requestMD5Agent);
    }
}
