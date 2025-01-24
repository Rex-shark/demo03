package com.example.demoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTModel {

    private String token;

    private String jit;

    private Date expiresAt;

    private Instant issuedAt;

}
