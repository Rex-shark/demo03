package com.example.demoapi.contorller;

import com.example.demoservice.mq.RabbitMQProducer;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {

    @Resource
    private  RabbitMQProducer producer;

    @GetMapping("/send/{message}")
    public String sendMessage(@PathVariable String message) {
        producer.sendMessage(message);
        return "✅ 消息已發送：" + message;
    }
}

