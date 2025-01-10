package com.example.demoapi.contorller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demoapi.request.LoginRequest;
import com.example.demoapi.response.JWTResponse;
import com.example.demoapi.response.WebResponse;


import com.example.demoapi.utils.JWTUtils;
import com.example.demoservice.entity.UserBase;
import com.example.demoservice.service.service.AuthService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @Resource
    private JWTUtils jwtUtils;

    @Value("${jwt.access-token-minute}")
    private int accessTokenMinute;

    @Value("${jwt.refresh-token-minute}")
    private int  refreshTokenMinute;


    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request , HttpServletRequest httpRequest)  {


        UserBase userBase = authService.authenticate(request.getAccount(),request.getPassword());

        if(userBase == null){
            return new ResponseEntity<>(new WebResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    "帳號密碼 錯誤"
            ), HttpStatus.BAD_REQUEST);
        }

        JWTResponse jwtResponse = new JWTResponse();
        String accessToken = "";
        String refreshToken = "";

        accessToken = jwtUtils.generateToken(request.getAccount(),1L,accessTokenMinute,null);
        refreshToken = jwtUtils.generateToken(request.getAccount(),1L, refreshTokenMinute,null);
        jwtResponse.setStatus(true);
        jwtResponse.setAccessToken(accessToken);
        jwtResponse.setRefreshToken(refreshToken);
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

        String newAccessToken = jwtUtils.generateToken(refreshJwtAccount,1L,120,null);
        String newRefreshAccessToken = jwtUtils.generateToken(refreshJwtAccount,1L,120,null);
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
