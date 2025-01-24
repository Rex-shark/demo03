package com.example.demoapi;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.demoservice.constant.ApiMessageEnum;
import com.example.demoapi.response.ApiDataResponse;
import com.example.demoapi.response.WebResponse;
import com.example.demoservice.exception.CRUDException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {
//    //TODO Exception攔截器範例 目前還沒決定 要攔截什麼Exception
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<?> handleIllegalArgumentException(HttpMessageNotReadableException ex
//            , HandlerMethod handlerMethod) {
//        //TokenExpiredException
//        System.out.println("A handleIllegalArgumentException 測試中");
//        System.out.println("handlerMethod.getClass().getName() = " + handlerMethod.getClass().getName());
//        System.out.println("handlerMethod.getMethod().getClass() = " + handlerMethod.getMethod().getClass());
//        System.out.println("handlerMethod.getMethod().getClass().getName() = " + handlerMethod.getMethod().getClass().getName());
//        System.out.println("handlerMethod.getMethod().getName() = " + handlerMethod.getMethod().getName());
//
//        return new ResponseEntity<>(new WebResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                HttpStatus.BAD_REQUEST.getReasonPhrase(),
//                "test"
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
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex , HandlerMethod handlerMethod) {
        //TODO handlerMethod 的資訊可以考慮紀錄Log

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

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // HTTP 401 未授權
    public ResponseEntity<?> handleTokenExpiredException(TokenExpiredException ex, HandlerMethod handlerMethod) {
        System.out.println("\"自訂 handle攔截\" = " + "自訂 handle攔截");

        return new ResponseEntity<>(new WebResponse(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                "JWT token is expired"
        ), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, HandlerMethod handlerMethod) {
        System.out.println("自訂 handle攔截 這好像用不到");
        return new ResponseEntity<>(new WebResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        ), HttpStatus.OK);
    }

    /*
        自訂Exception
     */
    @ExceptionHandler(CRUDException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> CRUDException(CRUDException ex, HandlerMethod handlerMethod) {
        String message = ex.getMessage();
        ApiMessageEnum e = ex.getApiMessageEnum();

        return new ResponseEntity<>(new ApiDataResponse<>(e,message), HttpStatus.OK);
    }
}