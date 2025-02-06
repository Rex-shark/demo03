package com.example.demoservice.mq;

import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.name}") // 從 application.properties 讀取佇列名稱
    private String queueName;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        System.out.println("📤 發送消息：" + message);
        rabbitTemplate.convertAndSend(queueName, message);
    }
}
