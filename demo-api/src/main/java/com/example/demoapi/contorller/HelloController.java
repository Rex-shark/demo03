package com.example.demoapi.contorller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demoapi.config.TestConfig;

import com.example.demoapi.request.LoginRequest;
import com.example.demoapi.response.WebResponse;

import com.example.demoapi.utils.JWTUtils;
import com.example.demoservice.entity.SysMenu;

import com.example.demoservice.entity.SysRole;
import com.example.demoservice.entity.SysRoleMenu;
import com.example.demoservice.entity.UserBase;
import com.example.demoservice.repository.ISysMenuRepository;
import com.example.demoservice.repository.ISysRoleMenuRepository;
import com.example.demoservice.repository.ISysRoleRepository;
import com.example.demoservice.repository.IUserBaseRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Slf4j
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/hello")
public class HelloController {
    @Resource
    private ISysRoleRepository sysRoleRepository;

    @Resource
    private ISysMenuRepository sysMenuRepository;

    @Resource
    private ISysRoleMenuRepository sysRoleMenuRepository;

    @Resource
    TestConfig testConfig;

    @Resource
    JWTUtils jwtUtils;

    @Resource
    IUserBaseRepository userBaseRepository;

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
        System.out.println("testConfig.getTestName() = " + testConfig.getTestName());

        String account = request.getAccount();
        String platformName ="demo";
//        List<SysMenu> sysMenuList = sysMenuRepo.findUserMenuList(account,platformName);
//        for (SysMenu sysMenu : sysMenuList) {
//            System.out.println("sysMenu.getMenuName() = " + sysMenu.getMenuName());
//        }
//        String message = "test ok "+ request.getAccount();

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
        Optional<UserBase> user = userBaseRepository.findById(1L);


        // 刪除用戶，JPA 自動刪除關聯數據
        userBaseRepository.delete(user.get());

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


    @PostMapping("p/jwt")
    public ResponseEntity<?> postJwt(@ModelAttribute LoginRequest loginRequest
    ,@RequestHeader("Authorization") String authHeader) throws Exception {
        System.out.println("postJwt authHeader = " + authHeader);

        try {
            String token = jwtUtils.extractToken(authHeader);
            System.out.println("token = " + token);
            DecodedJWT jwt = jwtUtils.validateToken(token);
            String account = jwt.getClaim("account").asString();
            System.out.println("account = " + account);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new WebResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    e.getMessage()
            ), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "msg"
        ), HttpStatus.OK);
    }


    @GetMapping("g/delete-user")
    @ResponseBody
    public ResponseEntity  deleteUser(@RequestParam("account") String account) throws Exception {
        System.out.println("account = " + account);
        Optional<UserBase> userBaseOptional =  userBaseRepository.findByAccount(account);

        if(userBaseOptional.isEmpty()){
            return new ResponseEntity<>(new WebResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    "刪除失敗"
            ), HttpStatus.BAD_REQUEST);
        }

        // 刪除用戶，JPA 自動刪除關聯數據
        userBaseRepository.delete(userBaseOptional.get());

        return new ResponseEntity<>(new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "刪除成功"
        ), HttpStatus.OK);
    }

    @GetMapping("g/ng4")
    public String  getNg4() throws Exception {
        System.out.println("444");
        Optional<SysRole> sysRoleOptional = sysRoleRepository.findByNid("USER");
        System.out.println("sysRoleOptional = " + sysRoleOptional);
        if(sysRoleOptional.isEmpty()){
            return "沒USER" ;
        }
        //要給USER 賦予菜單 nid = 我的訂單
        SysRole sysRole = sysRoleOptional.get();
        //找出nid = 我的訂單 的 子菜單與父菜單
        List<SysMenu> sysMenuList =  sysMenuRepository.findMenusByParentIdForNid("我的訂單");
        for (SysMenu sysMenu :sysMenuList) {
            // 創建 SysRoleMenu 關聯
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setSysRole(sysRole);  // 設定角色
            sysRoleMenu.setSysMenu(sysMenu);  // 設定菜單
            sysRoleMenu.setRemark("AUTO");  // 可選的備註
            // 儲存關聯
            sysRoleMenuRepository.save(sysRoleMenu);
        }

        return "OKOKOKOK" ;
    }
}
