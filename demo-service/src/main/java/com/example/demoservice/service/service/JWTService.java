package com.example.demoservice.service.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demoservice.entity.SysRole;
import com.example.demoservice.entity.UserBase;
import com.example.demoservice.model.JWTModel;
import com.example.demoservice.utils.CommonUtils;
import com.example.demoservice.utils.Utils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Service
public class JWTService {

    @Resource
    private Utils utils;

    @Value("${jwt.issuer}")
    private String issuer;

    private final Algorithm algorithm;

    //從設定檔中讀取密鑰
    public JWTService(@Value("${jwt.secret-key}") String secretKey) {
        this.algorithm = Algorithm.HMAC256(secretKey);
    }


    public JWTModel generateTokenModel(UserBase userBase, int minute ,ArrayList<String> roleList) throws NoSuchAlgorithmException {

        if(roleList == null || roleList.isEmpty()){
            //userBase 抓權限
            roleList = new ArrayList<>();
            for (SysRole role : userBase.getRoles()) {
                roleList.add(role.getNid());
            }
        }
        HttpServletRequest request = CommonUtils.getRequest();

        String clientIpAddress = CommonUtils.getIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        String token = "";
        String jti = UUID.randomUUID().toString();

        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(minute);
        Date expireTime = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        Instant now = Instant.now();

        token = JWT.create()
                .withClaim("account", userBase.getAccount())
                .withClaim("num", userBase.getId())//TODO id 放JWT是否合適?
                .withClaim("roles",roleList)
                .withClaim("ip",utils.hashWithMD5(clientIpAddress+ jti))
                .withClaim("agent",utils.hashWithMD5(userAgent+ jti))
                .withExpiresAt(expireTime)
                .withIssuer(issuer)
                .withIssuedAt(now)
                .withJWTId(jti)
                .sign(algorithm);

        return new JWTModel(token,jti,expireTime,now);
    }

    /*
        取得Bearer的 Token
     */
    public  String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new IllegalArgumentException("Authorization header is missing or invalid");
    }

    /*
        驗證Token
     */
    public DecodedJWT validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("demo Invalid or expired token", e);
        }
    }

    /*
        驗證Token 測試用
     */
    public JWTVerifier validateToken2(String token) {
        JWTVerifier verifier ;
        try {
            verifier = JWT.require(algorithm).build();
        } catch (JWTVerificationException e) {
            //TOKEN 亂打 會拋這
            throw new JWTVerificationException("demo Invalid or expired token", e);
        }
        return verifier;
    }
}
