package com.example.demoapi.contorller;

import com.example.demoapi.config.TestConfig;

import com.example.demoservice.request.AuthRequest;
import com.example.demoapi.response.WebResponse;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@Hidden
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Resource
    TestConfig testConfig;



    /**
     * Rex 胡亂功能測試用
     */
    @PostMapping("rex")
    @Operation(
            description = "登入",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerError"),
                    @ApiResponse(
                            responseCode = "200",
                            description = "登入",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 200, \"status\" : \"ok\", \"message\" : \"成功登入\"}"
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<?> rex(@Valid @RequestBody AuthRequest request , HttpServletRequest httpRequest
    ) throws Exception {
        System.out.println("httpRequest.getRemoteAddr() = " + httpRequest.getRemoteAddr());
        System.out.println("Account = "+ request.getAccount());
        System.out.println("testConfig.getTestName() = " + testConfig.getTestName());

        return new ResponseEntity<>(new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "message"
        ), HttpStatus.OK);
    }

    /**
     * get
     * 範例.1 基本型
     * 使用 @RequestParam 接收參數
     *
     */
    @GetMapping("g/demo1")
    public ResponseEntity<?> getDemo1(@RequestParam String account
            ,@RequestParam String password) throws Exception {

        String msg = "getDemo1 , account = " + account + " ,password = " + password;
        System.out.println("msg = " + msg);
        log.error("msg = {}", msg);
        log.info("account = {}", account);




        return new ResponseEntity<>(new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                msg
        ), HttpStatus.OK);
    }

    /**
     * get
     * 範例.2
     * 用model包裝參數
     * 增加 HttpServletRequest 取得 Request 相關屬性
     *
     * @param authRequest LoginRequest
     * @param request HttpServletRequest
     * @return
     * @throws Exception
     */
    @GetMapping("g/demo2")
    public ResponseEntity<?> getDemo2(@ModelAttribute AuthRequest authRequest
            , HttpServletRequest request) throws Exception {
        // 使用 HttpServletRequest 取得當前路徑
        String currentPath = request.getRequestURI();

        //BeanUtils.copyProperties();

        String msg = "currentPath = " + currentPath
                + " , account = " + authRequest.getAccount()
                + " , password = " + authRequest.getPassword();

        System.out.println("msg = " + msg);

        return new ResponseEntity<>(new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                msg
        ), HttpStatus.OK);
    }



}
