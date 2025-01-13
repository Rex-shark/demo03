package com.example.demoapi.contorller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demoapi.request.LoginRequest;
import com.example.demoapi.request.TokenRefreshRequest;
import com.example.demoapi.response.JWTResponse;
import com.example.demoapi.response.WebResponse;


import com.example.demoapi.utils.JWTUtils;
import com.example.demoservice.entity.UserBase;
import com.example.demoapi.service.AuthService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request , HttpServletRequest httpRequest
    , HttpServletResponse response)  {

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

        accessToken = authService.generateJwtToken(userBase,accessTokenMinute);
        refreshToken = authService.generateJwtToken(userBase,refreshTokenMinute);

        jwtResponse.setStatus(true);
        jwtResponse.setAccessToken(accessToken);
        //jwtResponse.setRefreshToken(refreshToken);
        jwtResponse.setAccount(request.getAccount());

        System.out.println("jwtResponse.toString() = " + jwtResponse.toString());

        //TODO 不確定RefreshToken 該放哪邊
        // 創建 HttpOnly Cookie
        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);       // 無法通過 JavaScript 訪問
        refreshCookie.setSecure(false);         // 僅在 HTTPS 下傳輸
        refreshCookie.setPath("/");            // 設置作用域為整個應用
        refreshCookie.setMaxAge(refreshTokenMinute/60); // 設置過期時間 單位秒

        // 將 Cookie 添加到響應中
        response.addCookie(refreshCookie);

        return new ResponseEntity<>(new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                jwtResponse.toString()
        ), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody TokenRefreshRequest tokenRefreshRequest
    ,@RequestHeader("Authorization") String authHeader
    ,HttpServletRequest request
    ,HttpServletResponse response) {
        System.out.println("tokenRefreshRequest = " + tokenRefreshRequest);
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

        DecodedJWT headerJwt = jwtUtils.validateToken(headerToken);
        DecodedJWT refreshJwt = jwtUtils.validateToken(refreshToken);

        String refreshJwtAccount = refreshJwt.getClaim("account").asString();
        String account = tokenRefreshRequest.getAccount();

        if(!refreshJwtAccount.equals(account)){
            return new ResponseEntity<>(new WebResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    "error token"
            ), HttpStatus.UNAUTHORIZED);
        }

        JWTResponse jwtResponse = new JWTResponse();
        jwtResponse.setStatus(true);

        int num = refreshJwt.getClaim("num").asInt();
        String role = refreshJwt.getClaim("role").asString();

        String newAccessToken = jwtUtils.generateToken(refreshJwtAccount, (long) num,accessTokenMinute,role);
        String newRefreshAccessToken = jwtUtils.generateToken(refreshJwtAccount,(long) num,refreshTokenMinute,role);

        jwtResponse.setAccessToken( newAccessToken);
        //jwtResponse.setRefreshToken(newRefreshAccessToken);
        jwtResponse.setAccount(refreshJwtAccount);

        //TODO 不確定RefreshToken 該放哪邊
        // 創建 HttpOnly Cookie
        Cookie refreshCookie = new Cookie("refreshToken", newRefreshAccessToken);
        refreshCookie.setHttpOnly(true);       // 無法通過 JavaScript 訪問
        refreshCookie.setSecure(false);         // 僅在 HTTPS 下傳輸
        refreshCookie.setPath("/");            // 設置作用域為整個應用
        refreshCookie.setMaxAge(refreshTokenMinute/60); // 設置過期時間 單位秒

        // 將 Cookie 添加到響應中
        response.addCookie(refreshCookie);

        return new ResponseEntity<>(new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                jwtResponse.toString()
        ), HttpStatus.OK);
    }
}
