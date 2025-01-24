package com.example.demoservice.exception;

import com.example.demoservice.constant.ApiMessageEnum;
import lombok.Getter;

@Getter
public class CRUDException extends RuntimeException {
    public CRUDException(String message) {
        super(message);
        this.apiMessageEnum = ApiMessageEnum.FAIL;
    }
    public CRUDException(String message, ApiMessageEnum apiMessageEnum) {
        super(message);
        this.apiMessageEnum = apiMessageEnum;
    }

    private final ApiMessageEnum apiMessageEnum;
}
