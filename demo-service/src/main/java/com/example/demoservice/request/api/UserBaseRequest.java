package com.example.demoservice.request.api;

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
    private String account;

    @JsonProperty("password")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    //add時前端不會知道uuid
//    @NotBlank(message = "uuid is mandatory")
//    @Size(min = 36, max = 36, message = "uuid size is 36")
    private String uuid;

    private String remark;

    private Integer status = 1;

    private List<String> sysRole ;


}

