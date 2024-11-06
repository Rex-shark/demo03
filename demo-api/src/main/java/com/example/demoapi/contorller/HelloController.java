package com.example.demoapi.contorller;

import com.example.demoapi.request.LoginRequest;
import com.example.demoapi.response.WebResponse;
import com.example.demoservice.model.SysMenu;
import com.example.demoservice.repository.ISysMenuRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoservice.service.LoginService;

import java.util.List;


@RestController
@RequestMapping("/api/hello")
public class HelloController {

    @Resource
    LoginService loginService;

    @Resource
    ISysMenuRepo sysMenuRepo;

    /**
     * Rex 胡亂功能測試用
     * @param request
     * @param httpRequest
     * @return
     * @throws Exception
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
    public ResponseEntity<?> rex(@Valid @RequestBody LoginRequest request , HttpServletRequest httpRequest
    ) throws Exception {
        System.out.println("httpRequest.getRemoteAddr() = " + httpRequest.getRemoteAddr());
        System.out.println("Account = "+ request.getAccount());
        loginService.test(request.getAccount());
        String account ="a";
        String platformName ="b";
        List<SysMenu> sysMenuList = sysMenuRepo.findUserMenuList(account,platformName);
        String message = "test ok "+ request.getAccount();

        return new ResponseEntity<>(new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                message
        ), HttpStatus.OK);
    }
}
