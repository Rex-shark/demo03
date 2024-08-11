package com.example.demoapi.contorller;

import com.example.demoapi.request.LoginRequest;
import com.example.demoapi.response.WebResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoservice.service.LoginService;


@RestController
@RequestMapping("/api/hello")
public class HelloController {

    @Resource
    LoginService loginService;

    /**
     * Rex 測試用
     * @param request
     * @param httpRequest
     * @return
     * @throws Exception
     */
    @PostMapping("rex")
    public ResponseEntity<?> rex(@Valid @RequestBody LoginRequest request , HttpServletRequest httpRequest
    ) throws Exception {
        System.out.println("httpRequest.getRemoteAddr() = " + httpRequest.getRemoteAddr());
        System.out.println("Account = "+ request.getAccount());
        loginService.test(request.getAccount());
        String message = "test ok "+ request.getAccount();

        return new ResponseEntity<>(new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                message
        ), HttpStatus.OK);
    }
}
