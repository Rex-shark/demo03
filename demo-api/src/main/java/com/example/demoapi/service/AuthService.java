package com.example.demoapi.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demoservice.model.JWTModel;
import com.example.demoservice.service.service.JWTService;

import com.example.demoservice.entity.UserBase;
import com.example.demoservice.repository.IUserBaseRepository;
import com.example.demoservice.utils.CommonUtils;
import com.example.demoservice.utils.Utils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * TODO 這個service 不確定要放在哪個模組，應該要放回service
 */
@Service
public class AuthService {
    @Resource
    private  IUserBaseRepository userBaseRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JWTService jwtService;

    @Resource
    private Utils utils;

    /**
     * 認證帳密是否正確，且是使用中的帳號(status = 1)
     *
     * @param account 帳號
     * @param password 密碼(未加密)
     * @return UserBase
     */
    public UserBase authenticate(String account, String password) {

        Optional<UserBase> userOptional = userBaseRepository.findByAccountAndStatus(account,1);

        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            return userOptional.get();
        }
        return null;

    }

    /**
     * 建立合法JWT
     * @param userBase UserBase
     * @param minute jwt有效時間
     * @return JWTModel
     * @throws NoSuchAlgorithmException 拋出後由Controller catch處理
     */
    public JWTModel generateJWTModel(UserBase userBase, int minute ) throws NoSuchAlgorithmException {

        return jwtService.generateTokenModel(userBase,minute,null);

    }

    /**
     *
     * 建立合法JWT
     * @param userBase UserBase
     * @param minute jwt有效時間
     * @param sysRoleNidList JWT的"roles"權限，若此欄位非null，則權限使用此欄位
     *                       否則去userBase.roles中抓權限
     *
     * @return JWTModel
     * @throws NoSuchAlgorithmException 拋出後由Controller catch處理
     */
    public JWTModel generateJWTModel(UserBase userBase, int minute , ArrayList<String> sysRoleNidList) throws NoSuchAlgorithmException {

        return jwtService.generateTokenModel(userBase,minute,sysRoleNidList);

    }

    /**
     * 驗token的Ip與瀏覽器是否與Request()相同
     * @param jwt DecodedJWT
     * @return boolean
     * @throws NoSuchAlgorithmException 拋出後由Controller catch處理
     */
    public boolean validateToken(DecodedJWT jwt) throws NoSuchAlgorithmException {
        HttpServletRequest request = CommonUtils.getRequest();

        String jwtIp = jwt.getClaim("ip").asString();
        String jwtAgent = jwt.getClaim("agent").asString();
        String jti = jwt.getClaim("jti").asString();

        String requestMD5Ip = utils.hashWithMD5(CommonUtils.getIpAddress(request) + jti);
        String requestMD5Agent = utils.hashWithMD5(request.getHeader("User-Agent") + jti);

        return jwtIp != null && jwtIp.equals(requestMD5Ip) &&
                jwtAgent != null && jwtAgent.equals(requestMD5Agent);
    }

    /**
     * 驗token的Ip與瀏覽器是否與Request()相同
     * 先接受完整JWT，再由jwtService.validateToken(token)
     * 驗證是否合法並轉換為DecodedJWT後，才驗證Ip與瀏覽器
     *
     * @param token String token
     * @return boolean
     * @throws NoSuchAlgorithmException 拋出後由Controller catch處理
     */
    public boolean validateToken(String token) throws NoSuchAlgorithmException {
        return this.validateToken(jwtService.validateToken(token));
    }


}
