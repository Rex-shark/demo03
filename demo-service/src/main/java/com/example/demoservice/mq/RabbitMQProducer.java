package com.example.demoservice.mq;

import com.example.demoservice.model.LogMessageQueueModel;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Resource
    private  RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.name.log}") // 從 application.properties 讀取佇列名稱
    private String logQueueName;

    public void saveLoginLog(LogMessageQueueModel model) {
        System.out.println("📤 發送消息 紀錄login");
        rabbitTemplate.convertAndSend(logQueueName, model);
    }

    public void sendMessage(String message) {
        System.out.println("📤 發送消息：" + message);
        rabbitTemplate.convertAndSend(logQueueName, message);
    }
}
