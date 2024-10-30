package com.example.demoapi;

import com.example.demoapi.response.WebResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {
    //TODO Exception攔截器範例 目前還沒決定 要攔截什麼Exception
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<?> handleIllegalArgumentException(HttpMessageNotReadableException ex
//            , HandlerMethod handlerMethod) {
//        System.out.println("A handleIllegalArgumentException 測試中");
//        System.out.println("handlerMethod.getClass().getName() = " + handlerMethod.getClass().getName());
//        System.out.println("handlerMethod.getMethod().getClass() = " + handlerMethod.getMethod().getClass());
//        System.out.println("handlerMethod.getMethod().getClass().getName() = " + handlerMethod.getMethod().getClass().getName());
//        System.out.println("handlerMethod.getMethod().getName() = " + handlerMethod.getMethod().getName());
//
//        return new ResponseEntity<>(new WebResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                HttpStatus.BAD_REQUEST.getReasonPhrase(),
//                "Rex test aabbcc"
//        ), HttpStatus.BAD_REQUEST);
//    }

    /**
     * 攔截器
     * 驗證api的請求參數 api @Valid
     *
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        //這邊可自行定義 回傳的code 與 status
        return new ResponseEntity<>(new WebResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                String.join(", ", errorMessages)
        ), HttpStatus.BAD_REQUEST);
    }

}