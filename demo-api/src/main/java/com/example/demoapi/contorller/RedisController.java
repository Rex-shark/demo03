package com.example.demoapi.contorller;

import com.example.demoservice.entity.UserBase;
import com.example.demoservice.service.service.RedisService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "2.Redis",description = "Redis 操作")
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    RedisService redisService;

    @GetMapping("{key}")
    public ResponseEntity<?> getRedis(
            @PathVariable String key) {
        System.out.println("key = " + key);
        Object obj = redisService.getValue(key);

        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PutMapping("{key}")
    public ResponseEntity<?> saveRedis(@PathVariable String key,
                                      @RequestParam String val,
                                      @RequestParam Integer time){
        System.out.println("更新使用者 key = " + key);
        redisService.setValue(key,val,time);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{key}")
    public ResponseEntity<?> deleteRedis(@PathVariable String key) {
        System.out.println("刪除 Redis key = " + key);
        redisService.deleteValue(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
      查所有白名單
    */
    @GetMapping("/white-list")
    public ResponseEntity<?> getAllWhiteList() {
        Set<String> s = redisService.getAllWhiteListedJti();
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    /*
        查帳號有沒有在白名單
     */
    @GetMapping("/white-list/{account}")
    public ResponseEntity<?> getWhiteList(@PathVariable String account) {
        System.out.println(" 查帳號有沒有在白名單 account = " + account);
        boolean f =  redisService.isWhiteListed(account);

        return new ResponseEntity<>(f, HttpStatus.OK);
    }

    /*
      查所有黑名單
    */
    @GetMapping("/black-list")
    public ResponseEntity<?> getBlackList() {
        Set<String> s = redisService.getAllBlackListedJti();
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    /*
      查JWT有沒有在黑名單
    */
    @GetMapping("/black-list/{jti}")
    public ResponseEntity<?> getBlack(@PathVariable String jti) {
        boolean f =  redisService.isBlackListed(jti);
        return new ResponseEntity<>(f, HttpStatus.OK);
    }


}
