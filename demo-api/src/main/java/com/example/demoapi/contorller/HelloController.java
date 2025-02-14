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


    @GetMapping("GDMS_AUTH_SSO.asmx/Auth")
    public String authTest(@RequestParam String id, @RequestParam String pwd, HttpServletRequest request) {
        // 取得請求方的 IP
        String clientIp = request.getRemoteAddr();

        // 取得請求的完整 URL
        String requestUrl = request.getRequestURL().toString();

        // 取得 User-Agent（用戶裝置資訊）
        String userAgent = request.getHeader("User-Agent");

        // 取得 Referer（從哪個頁面來的）
        String referer = request.getHeader("Referer");

        // 印出請求資訊
        System.out.println("🔹 ID：" + id);
        System.out.println("🔹 PWD：" + pwd);
        System.out.println("🔹 發送者 IP：" + clientIp);
        System.out.println("🔹 請求網址：" + requestUrl);
        System.out.println("🔹 User-Agent：" + userAgent);
        System.out.println("🔹 來源網址：" + (referer != null ? referer : "無"));

        return "✅ 認證測試成功！\n" +
                "🔹 ID：" + id + "\n" +
                "🔹 PWD：" + pwd + "\n" +
                "🔹 發送者 IP：" + clientIp + "\n" +
                "🔹 請求網址：" + requestUrl + "\n" +
                "🔹 User-Agent：" + userAgent + "\n" +
                "🔹 來源網址：" + (referer != null ? referer : "無");
    }
}
