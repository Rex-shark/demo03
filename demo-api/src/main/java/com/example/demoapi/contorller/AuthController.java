package com.example.demoapi.contorller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demoapi.request.LoginRequest;
import com.example.demoapi.response.JWTResponse;
import com.example.demoapi.response.WebResponse;


import com.example.demoapi.utils.JWTUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    JWTUtils jwtUtils;


    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request , HttpServletRequest httpRequest)  {

        //先驗證 帳號密碼
        boolean flag = request.getAccount().equals("admin")&&request.getPassword().equals("123456"); //驗證帳號密碼

        if(!flag){
            return new ResponseEntity<>(new WebResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    "帳號密碼 錯誤"
            ), HttpStatus.BAD_REQUEST);
        }

        JWTResponse jwtResponse = new JWTResponse();
        String accessToken = "";
        String refreshToken = "";

        accessToken = jwtUtils.generateToken(request.getAccount(),1L,120);
        refreshToken = jwtUtils.generateToken(request.getAccount(),1L,1200);
        jwtResponse.setStatus(true);
        jwtResponse.setAccessToken( accessToken);
        jwtResponse.setRefreshToken( refreshToken);
        jwtResponse.setAccount(request.getAccount());


        System.out.println("jwtResponse.toString() = " + jwtResponse.toString());
        return new ResponseEntity<>(new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                jwtResponse.toString()
        ), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody String account
    ,@RequestHeader("Authorization") String authHeader
    ,HttpServletRequest request) {
        System.out.println("account = " + account);
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return new ResponseEntity<>(new WebResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    "not token"
            ), HttpStatus.UNAUTHORIZED);
        }

        String refreshToken = "";
        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                refreshToken = cookie.getValue();
            }
        }

        String headerToken = jwtUtils.extractToken(authHeader);

        System.out.println("headerToken = " + headerToken);
        System.out.println("refreshToken = " + refreshToken);

        DecodedJWT headerJwt = jwtUtils.validateToken(headerToken);
        DecodedJWT refreshJwt = jwtUtils.validateToken(refreshToken);

        String refreshJwtAccount = refreshJwt.getClaim("account").asString();
        if(!refreshJwtAccount.equals(account)){
            return new ResponseEntity<>(new WebResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    "error token"
            ), HttpStatus.UNAUTHORIZED);
        }

        JWTResponse jwtResponse = new JWTResponse();
        jwtResponse.setStatus(true);

        String newAccessToken = jwtUtils.generateToken(refreshJwtAccount,1L,120);
        String newRefreshAccessToken = jwtUtils.generateToken(refreshJwtAccount,1L,120);
        jwtResponse.setAccessToken( newAccessToken);
        jwtResponse.setRefreshToken(newRefreshAccessToken);
        jwtResponse.setAccount(refreshJwtAccount);

        return new ResponseEntity<>(new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                jwtResponse.toString()
        ), HttpStatus.OK);
    }
}
