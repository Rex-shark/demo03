package com.example.demoservice.service.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelper {
    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof DecodedJWT decodedJWT) {
            System.out.println("decodedJWT.getClaim(\"num\").asLong() = " + decodedJWT.getClaim("num").asLong());
            return decodedJWT.getClaim("num").asLong();
        }else{
            return 1L;
        }
    }
}
