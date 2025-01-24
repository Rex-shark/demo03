package com.example.demoservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"account","password"})
public class AuthRequest {
    @JsonProperty("account")
    @NotBlank(message = "Account is mandatory")
    @Size(min = 1, max = 20, message = "Account must be between 1 and 20 characters")
    private String account;

    @JsonProperty("password")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @JsonProperty("code")
    @NotBlank(message = "code is mandatory")
    private String code;

    @JsonProperty("accessToken")
    private String accessToken;
}
