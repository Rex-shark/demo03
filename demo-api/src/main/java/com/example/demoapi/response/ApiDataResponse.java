package com.example.demoapi.response;

import com.example.demoservice.constant.ApiMessageEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiDataResponse<T> {

    public ApiDataResponse( ApiMessageEnum e ) {
        this.isOk = e.getIsOk();
        this.code = e.getCode();
        this.message =  e.getMessage();
        this.data = null;
    }

    //TODO 這種設計應該是錯誤，導致swagger無法正確顯示
    public ApiDataResponse( ApiMessageEnum e ,String message) {
        this.isOk = e.getIsOk();
        this.code = e.getCode();
        this.message = message;
        this.data = null;
    }

    public ApiDataResponse(T singleObject , ApiMessageEnum e ) {
        this.isOk = e.getIsOk();
        this.code = e.getCode();
        this.message =  e.getMessage();
        this.data = singleObject;

    }

    @JsonProperty("isOk")
    private Boolean isOk;
    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private T data;
}
