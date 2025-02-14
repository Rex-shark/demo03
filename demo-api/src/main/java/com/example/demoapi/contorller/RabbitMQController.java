package com.example.demoapi.contorller;

import com.example.demoservice.mq.RabbitMQProducer;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {

    @Resource
    private  RabbitMQProducer producer;

    @GetMapping("/send/{message}")
    public String sendMessage(@PathVariable String message, HttpServletRequest request) {
        // 取得請求方的 IP
        String clientIp = request.getRemoteAddr();

        // 取得請求的完整 URL
        String requestUrl = request.getRequestURL().toString();

        // 取得 User-Agent（用戶裝置資訊）
        String userAgent = request.getHeader("User-Agent");

        // 取得 Referer（從哪個頁面來的）
        String referer = request.getHeader("Referer");

        // 印出請求資訊
        System.out.println("📩 接收到請求:");
        System.out.println("🔹 發送者 IP：" + clientIp);
        System.out.println("🔹 請求網址：" + requestUrl);
        System.out.println("🔹 User-Agent：" + userAgent);
        System.out.println("🔹 來源網址：" + (referer != null ? referer : "無"));

        // 發送消息
        producer.sendMessage(message);

        return "✅ 消息已發送：" + message + "\n" +
                "🔹 發送者 IP：" + clientIp + "\n" +
                "🔹 請求網址：" + requestUrl + "\n" +
                "🔹 User-Agent：" + userAgent + "\n" +
                "🔹 來源網址：" + (referer != null ? referer : "無");
    }
}

