package com.example.demoservice.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBaseRequest {
    @JsonProperty("account")
    @NotBlank(message = "Account is mandatory")
    @Size(min = 1, max = 20, message = "Account must be between 1 and 20 characters")
    private String account;

    @JsonProperty("password")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "uuid is mandatory")
    @Size(min = 36, max = 36, message = "uuid size is 36")
    private String uuid;

    private List<String> sysRole ;


}

