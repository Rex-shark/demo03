package com.example.demoapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}