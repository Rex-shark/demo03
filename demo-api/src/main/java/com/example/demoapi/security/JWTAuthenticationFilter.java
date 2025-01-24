package com.example.demoapi.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demoapi.service.AuthService;
import com.example.demoservice.service.service.JWTService;
import com.example.demoservice.service.service.RedisService;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JWTService jwtService;

    @Resource
    private AuthService authService;

    @Resource
    private RedisService redisService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println("doFilterInternal path = " + path);

        String authHeader = request.getHeader("Authorization");
        System.out.println("authHeader = " + authHeader);


        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = jwtService.extractToken(authHeader);

                DecodedJWT decodedJWT = jwtService.validateToken(token);

                String account = decodedJWT.getClaim("account").asString();
                String jti = decodedJWT.getClaim("jti").asString();

                System.out.println("jti = " + jti);

                //驗IP跟瀏覽器
                boolean isValidateToken = authService.validateToken(token);

                //驗證沒過
                if(!isValidateToken ){
                    // 這邊表示token被亂用 應該直接送403
                    log.error("!isValidateToken token{}", token);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Unauthorized: Invalid or expired token");
                    return;
                }

                //驗黑單
                boolean isBlack =  redisService.isBlackListed(jti);

                //在黑名單
                if(isBlack){
                    //在黑名單，要重刷，先送401
                    log.error("isBlack token{}", token);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 設置 HTTP 狀態碼 401
                    response.getWriter().write("Invalid token");
                    return;
                }

                List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
                List<GrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());
                // 設置認證
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(decodedJWT, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JWTVerificationException e) {
                //過期或假token，都會來這，分不出來
                log.error("JWTException message(){}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token");
                return;
            } catch (Exception e) {
                //未知錯誤，送他403
                log.error("Exception message(){}", e.getMessage(), e);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Invalid or expired token");
                return;
            }
        }else{
            System.out.println("JWTAuthenticationFilter OK");
        }
        //RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        filterChain.doFilter(request, response);
    }
}
