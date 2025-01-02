package com.example.demoapi.contorller;

import com.example.demoapi.request.LoginRequest;
import com.example.demoapi.response.WebResponse;
import com.example.demoservice.model.SysMenu;
import com.example.demoservice.repository.ISysMenuRepo;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.example.demoservice.service.LoginService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@CrossOrigin(origins = {"http://localhost:4200"})
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

    /**
     * get
     * 範例.1 基本型
     * 使用 @RequestParam 接收參數
     *
     * @param account String
     * @param password String
     * @return
     * @throws Exception
     */
    @GetMapping("g/demo1")
    public ResponseEntity<?> getDemo1(@RequestParam String account
            ,@RequestParam String password) throws Exception {

        String msg = "getDemo1 , account = " + account + " ,password = " + password;
        System.out.println("msg = " + msg);

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
     * @param loginRequest LoginRequest
     * @param request HttpServletRequest
     * @return
     * @throws Exception
     */
    @GetMapping("g/demo2")
    public ResponseEntity<?> getDemo2(@ModelAttribute LoginRequest loginRequest
            , HttpServletRequest request) throws Exception {
        // 使用 HttpServletRequest 取得當前路徑
        String currentPath = request.getRequestURI();

        //BeanUtils.copyProperties();

        String msg = "currentPath = " + currentPath
                + " , account = " + loginRequest.getAccount()
                + " , password = " + loginRequest.getPassword();

        System.out.println("msg = " + msg);

        return new ResponseEntity<>(new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                msg
        ), HttpStatus.OK);
    }

    @PostMapping("p/demo")
    public ResponseEntity<?> postDemo1() throws Exception {
        System.out.println("demo api");

        String msg = "account = " ;
        System.out.println("msg = " + msg);

        return new ResponseEntity<>(new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                msg
        ), HttpStatus.OK);
    }


    @GetMapping("g/ng1")
    public ResponseEntity<?> getNg1(@RequestParam String name) throws Exception {
        // 使用 HttpServletRequest 取得當前路徑
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        String msg = name + "您好:" + "現在時間" + formattedTime;

        System.out.println("msg = " + msg);
        JsonObject json = new JsonObject();

        // 加入屬性
        json.addProperty("id", 1);
        json.addProperty("name", "小明");




        // 使用 Gson 來序列化 JsonObject
        Gson gson = new Gson();
        String jsonString = gson.toJson(json);

        return new ResponseEntity<>(new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                msg
        ), HttpStatus.OK);
    }

    @GetMapping("g/ng2")
    public JsonObject  getNg2() throws Exception {

        System.out.println("aaaaa");
        JsonObject json = new JsonObject();

        // 加入屬性
        json.addProperty("id", 1);
        json.addProperty("name", "小明");


        // 使用 Gson 來序列化 JsonObject
        Gson gson = new Gson();
        String jsonString = gson.toJson(json);

        return json ;
    }

    @GetMapping("g/ng3")
    public String  getNg3() throws Exception {
        System.out.println("333");
        return "OKOKOKOK" ;
    }
}
