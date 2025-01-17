package com.example.demoservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demoservice.entity.SysRole;
import com.example.demoservice.entity.UserBase;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

@Component
public class JWTUtils {

    @Resource
    private  Utils utils;

    private final Algorithm algorithm;

    //從設定檔中讀取密鑰
    public JWTUtils(@Value("${jwt.secret-key}") String secretKey) {
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    @Value("${jwt.issuer}")
    private String issuer;


    public  String generateToken(String account,Long userBaseId,int minute,String roles) {
        String token = "";

        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(minute);
        Date expireTime = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        token = JWT.create()
                .withClaim("account", account)
                .withClaim("num", userBaseId)
                .withClaim("roles", roles)
                .withExpiresAt(expireTime)
                .withIssuer(issuer)
                .withIssuedAt(Instant.now())
                .sign(algorithm);
        return token;
    }

    public  String generateToken(UserBase userBase, int minute) {
        ArrayList<String> roleList = new ArrayList<>();
        for (SysRole role : userBase.getRoles()) {
            roleList.add(role.getNid());
        }

        String token = "";

        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(minute);
        Date expireTime = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        token = JWT.create()
                .withClaim("account", userBase.getAccount())
                .withClaim("num", userBase.getId())
                .withClaim("roles",roleList)
                .withExpiresAt(expireTime)
                .withIssuer(issuer)
                .withIssuedAt(Instant.now())
                .sign(algorithm);
        return token;
    }

    /*
     暫定版本，有傳入HttpServletRequest，需要抓一些Request參數放入token，作為安全性驗證
     */
    public  String generateToken(UserBase userBase, int minute , HttpServletRequest request) throws NoSuchAlgorithmException {

        //userBase 抓權限
        ArrayList<String> roleList = new ArrayList<>();
        for (SysRole role : userBase.getRoles()) {
            roleList.add(role.getNid());
        }

        String clientIp = utils.getIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        String token = "";

        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(minute);
        Date expireTime = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        token = JWT.create()
                .withClaim("account", userBase.getAccount())
                .withClaim("num", userBase.getId())//可能要加密或改用uuid
                .withClaim("roles",roleList)
                .withClaim("ip",utils.hashWithMD5(clientIp))
                .withClaim("agent",utils.hashWithMD5(userAgent))
                .withExpiresAt(expireTime)
                .withIssuer(issuer)
                .withIssuedAt(Instant.now())
                .sign(algorithm);
        return token;
    }

    public  String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new IllegalArgumentException("Authorization header is missing or invalid");
    }

    public  DecodedJWT validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("Invalid or expired token", e);
        }
    }

}
