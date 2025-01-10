package com.example.demoapi.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demoservice.entity.UserBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

@Component
public class JWTUtils {

    private final Algorithm algorithm;

    //從設定檔中讀取密鑰
    public JWTUtils(@Value("${jwt.secret-key}") String secretKey) {
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    @Value("${jwt.issuer}")
    private String issuer;


    public  String generateToken(String account,Long userBaseId,int minute,ArrayList<String> roles) {
        String token = "";
        //時效minute分鐘
        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(minute);
        Date expireTime = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        ArrayList<String> list = new ArrayList();
        list.add("ADMIN");
        token = JWT.create()
                .withClaim("account", account)
                .withClaim("num", userBaseId)
                .withClaim("roles", list)
                .withExpiresAt(expireTime)
                .withIssuer(issuer)
                .withIssuedAt(Instant.now())
                .sign(algorithm);
        return token;
    }

    public  String generateToken(UserBase userBase, int minute, ArrayList<String> roles) {
        String token = "";
        //時效minute分鐘
        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(minute);
        Date expireTime = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        ArrayList<String> list = new ArrayList();
        list.add("ADMIN");
        token = JWT.create()
                .withClaim("account", userBase.getAccount())
                .withClaim("num", userBase.getId())
                .withClaim("roles", list)
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
