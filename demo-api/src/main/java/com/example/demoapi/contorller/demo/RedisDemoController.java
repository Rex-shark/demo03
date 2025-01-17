package com.example.demoapi.contorller.demo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/hello")
public class RedisDemoController {


    /*
        redisDemo 方法練習
     */
    @GetMapping("g/redis-demo")
    @ResponseBody
    public ResponseEntity<?> redisDemo(@RequestParam String text , HttpServletRequest httpRequest
            , HttpServletResponse response)  {
        System.out.println("account = " + text);
        return new ResponseEntity<>("測試OK "+text, HttpStatus.OK);
    }
}
