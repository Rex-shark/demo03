package com.example.demoservice.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.name}") // 監聽佇列
    public void receiveMessage(String message) {
        System.out.println("📥 接收消息：" + message);
    }
}

