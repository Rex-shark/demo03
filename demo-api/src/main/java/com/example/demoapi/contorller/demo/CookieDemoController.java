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
public class CookieDemoController {

    /*
        設定cookie練習
     */
    @GetMapping("g/cookie-demo")
    @ResponseBody
    public ResponseEntity<?> setCookieDemo(@RequestParam String text , HttpServletRequest httpRequest
            , HttpServletResponse response)  {
        System.out.println("account = " + text);
        /*
        有些教學說refreshToken放Cookie，又有說不用的，看需求設計
        這邊先練習放資料進Cookie

        讀取Cookie範例 Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
         */

        // 創建 HttpOnly Cookie
        Cookie demoCookie = new Cookie("demoCookie", "ABC"+text);
        demoCookie.setHttpOnly(true);       // 無法通過 JavaScript 訪問
        demoCookie.setSecure(false);         // 僅在 HTTPS 下傳輸
        demoCookie.setPath("/");            // 設置作用域為整個應用
        demoCookie.setMaxAge(7 * 24 * 60 * 60); // 設置過期時間（7天）

        //將 Cookie 添加到響應中
        response.addCookie(demoCookie);

        return new ResponseEntity<>("測試OK "+text, HttpStatus.OK);
    }
}
