package com.example.demoapi.contorller;

import com.example.demoservice.mq.RabbitMQProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {

    private final RabbitMQProducer producer;

    public RabbitMQController(RabbitMQProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/send/{message}")
    public String sendMessage(@PathVariable String message) {
        producer.sendMessage(message);
        return "✅ 消息已發送：" + message;
    }
}

