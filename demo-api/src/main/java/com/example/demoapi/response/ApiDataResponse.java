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

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("data", singleObject);
        this.data = dataMap;
    }

    @JsonProperty("isOk")
    private Boolean isOk;
    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private Map<String, Object> data;
}
