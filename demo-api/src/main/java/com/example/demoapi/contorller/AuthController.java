package com.example.demoapi.contorller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demoapi.response.ApiDataResponse;
import com.example.demoservice.constant.ApiMessageEnum;
import com.example.demoservice.entity.SysRole;
import com.example.demoservice.model.JWTModel;
import com.example.demoservice.model.LogMessageQueueModel;
import com.example.demoservice.mq.RabbitMQProducer;
import com.example.demoservice.repository.ISysRoleRepository;
import com.example.demoservice.request.AuthRequest;


import com.example.demoservice.service.service.JWTService;
import com.example.demoservice.service.service.RedisService;

import com.example.demoservice.entity.UserBase;
import com.example.demoapi.service.AuthService;
import com.example.demoservice.utils.CommonUtils;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Tag(name = "A.授權登入Controller",description = "登入登出與取得刷新Token")
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private RabbitMQProducer producer;

    @Resource
    private AuthService authService;

    @Resource
    private RedisService redisService;

    @Resource
    private JWTService jwtService;

    @Resource
    private ISysRoleRepository sysRoleRepository;

    @Value("${jwt.access-token-minute}")
    private int accessTokenMinute;

    @Value("${jwt.refresh-token-minute}")
    private int refreshTokenMinute;

    @Operation(summary = "登入",description = "輸入帳密，取得Token。目前沒有圖形驗證。")
    @ApiResponses({
            //TODO 研究如何通用簡化
            @ApiResponse(responseCode="200",description="正常"),
            @ApiResponse(responseCode="404",description="找不到路徑")
    })
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest
            , HttpServletRequest httpServletRequest
            , HttpServletResponse response)  {

        System.out.println("CommonUtils.getRequest() = " + CommonUtils.getRequest());

        //TODO 要做圖形驗證碼 驗 request.getCode()
        UserBase userBase = authService.authenticate(authRequest.getAccount(),authRequest.getPassword());

        if(userBase == null){
            return new ResponseEntity<>(new ApiDataResponse<>(ApiMessageEnum.AUTH_FAIL,"帳號密碼錯誤"), HttpStatus.OK);
        }

        JWTModel jwtModel ;
        String account = authRequest.getAccount();

        try {
            jwtModel = authService.generateJWTModel(userBase,accessTokenMinute);
        } catch (NoSuchAlgorithmException e) {
            //應該不太可能觸發這邊
            return new ResponseEntity<>(new ApiDataResponse<>(ApiMessageEnum.AUTH_FAIL,"MD5 error"), HttpStatus.OK);
        }

        //加入白單
        redisService.addToWhiteList(account , String.valueOf(jwtModel.getIssuedAt()));

        LogMessageQueueModel model = new LogMessageQueueModel();
        model.setAccount(account);
        model.setJwt(jwtModel.getToken());
        model.setIpAddress(CommonUtils.getIpAddress(httpServletRequest));
        model.setType(1);
        ZoneId taipeiZone = ZoneId.of("Asia/Taipei");
        model.setLoginTime(Date.from(jwtModel.getIssuedAt().atZone(taipeiZone).toInstant()));
        //TODO 序列化失敗 要修正
       // producer.saveLoginLog(model);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("accessToken", jwtModel.getToken());
        responseMap.put("jti", jwtModel.getJit());
        responseMap.put("account", account );

        return new ResponseEntity<>(new ApiDataResponse<>(responseMap,ApiMessageEnum.AUTH_SUCCESS), HttpStatus.OK);

    }

    /*
        登出
        要註銷JWT
     */
    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<?> logout( @RequestBody AuthRequest request
                                    , HttpServletRequest httpRequest)  {
        //TODO request.getAccount() 沒用到，也可移除
        System.out.println("登出 account = " + request.getAccount());


        boolean isValidateToken = false;
        String token = request.getAccessToken();

        try {
            isValidateToken = authService.validateToken(token);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.OK);
        }

        if(isValidateToken){
            DecodedJWT jwt = jwtService.validateToken(token);
            jwt.getClaim("account").asString();
            //加入黑名單
            redisService.addToBlackList(jwt.getClaim("jti").asString(),"USER logout");
            //移除白單
            redisService.deleteWhiteList(jwt.getClaim("account").asString());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     測試過期token
    */
    @Hidden
    @PostMapping("/logout2")
    @ResponseBody
    public ResponseEntity<?> logout2( @RequestBody AuthRequest request
            , HttpServletRequest httpRequest)  {
        System.out.println("account = " + request.getAccount());
        System.out.println("request.getAccessToken() = " + request.getAccessToken());

        String token = request.getAccessToken();
        System.out.println("token = " + token);
        JWTVerifier verifier;
        DecodedJWT jwt;

        try {
            verifier = jwtService.validateToken2(token);
        } catch (JWTVerificationException e) {
            System.out.println("AAAAB");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }catch(Exception e){
            System.out.println("BBBBB");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {

            jwt =  verifier.verify(token);
            Date exp = jwt.getExpiresAt();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 自訂格式
            String formattedDate = sdf.format(exp); // 格式化日期
            System.out.println("過期時間： " + formattedDate);
        } catch (JWTVerificationException e) {
            //直送404
            System.out.println("CCCCC");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }catch(Exception e){
            System.out.println("DDDDD");
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }


    /*
        開發階段用，自訂JWT
     */
    @PostMapping("/build-jwt")
    @ResponseBody
    public ResponseEntity<?> buildJwt(@RequestBody Map<String, Object> requestBody
            , HttpServletRequest httpRequest) {

        String account = (String) requestBody.get("account");
        Long id = ((Integer) requestBody.get("id")).longValue();
        int time = (Integer) requestBody.get("time");
        ArrayList<String> sysRoleList = (ArrayList<String>) requestBody.get("sysRole");

        System.out.println("account = " + account);
        System.out.println("id = " + id);
        System.out.println("time = " + time);

        JWTModel jwtModel ;
        UserBase userBase = new UserBase();
        userBase.setAccount(account);
        userBase.setId(id);

        for (String nid : sysRoleList) {
            Optional<SysRole> SysRole = sysRoleRepository.findByNid(nid);
            SysRole.ifPresent(sysRole -> userBase.getRoles().add(sysRole));
        }

        try {
            jwtModel = authService.generateJWTModel(userBase,time);
        } catch (NoSuchAlgorithmException e) {
            //應該不太可能觸發這邊
            return new ResponseEntity<>(new ApiDataResponse<>(ApiMessageEnum.AUTH_FAIL,"MD5 error"), HttpStatus.OK);
        }

        return new ResponseEntity<>(jwtModel,HttpStatus.OK);
    }

    /**
     * 更新憑證
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody AuthRequest authRequest
    ,HttpServletRequest httpRequest
    ,HttpServletResponse response) {
        Algorithm algorithm =  Algorithm.HMAC256("demo-Key");
        System.out.println("tokenRefreshRequest = " + authRequest);
        System.out.println("authRequest.getAccessToken() = " + authRequest.getAccessToken());
        String token = authRequest.getAccessToken();

        System.out.println("token = " + token);
        JWTVerifier verifier;
        DecodedJWT jwt;
        String account;
        try {
            //稍微過期也視為有效
            verifier = JWT.require(algorithm).acceptExpiresAt(refreshTokenMinute * 60L).build();
            jwt = verifier.verify(token);
            account = jwt.getClaim("account").asString();
            String jti = jwt.getClaim("jti").asString();

            //TODO 這邊重複程式碼
            //驗是否同IP、瀏覽器
            boolean isValidateToken = authService.validateToken(jwt);
            if(!isValidateToken ){
                //這邊表示token被亂用 直接送403
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            System.out.println("jti = " + jti);

            boolean isBlack =  redisService.isBlackListed(jti);
            if(isBlack){
                //在黑名單，不給refresh
                log.info("refresh黑名單token。token={}", token);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            boolean isWhite =  redisService.isWhiteListed(account);
            if(!isWhite){
                //不再白名單，不給refresh
                log.info("refresh非白單token。token={}", token);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            //都OK 給新TOKEN

        } catch(Exception e){
            log.info("無效 refreshAccessToken。token={} message={}", token, e.getMessage(),e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        JWTModel jwtModel ;
        UserBase userBase = new UserBase();

        try {

            ArrayList<String> sysRoleNidList = (ArrayList<String>) jwt.getClaim("roles").asList(String.class);
            userBase.setAccount(account);
            userBase.setId((long) jwt.getClaim("num").asInt());
            jwtModel = authService.generateJWTModel(userBase,accessTokenMinute,sysRoleNidList);

        } catch (NoSuchAlgorithmException e) {
            //應該不太可能觸發這邊
            return new ResponseEntity<>(new ApiDataResponse<>(ApiMessageEnum.AUTH_FAIL,"MD5 error"), HttpStatus.OK);
        }

        //加入白單
        redisService.addToWhiteList(account , String.valueOf(jwtModel.getIssuedAt()));

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("accessToken", jwtModel.getToken());
        responseMap.put("jti", jwtModel.getJit());
        responseMap.put("account", account );

        return new ResponseEntity<>(new ApiDataResponse<>(responseMap,ApiMessageEnum.AUTH_SUCCESS), HttpStatus.OK);
    }


}
