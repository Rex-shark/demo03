package com.example.demoapi.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * 待修正 這是很早期的設計
 */
@Data
public class JWTResponse {
    private String accessToken;
    private String refreshToken;
    private Boolean status;
    private String account;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting object to JSON string", e);
        }
    }
}
