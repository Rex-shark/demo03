package com.example.demoservice.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name}")
    private String queueName;  // 從 application.properties 讀取佇列名稱

    @Bean
    public Queue myQueue() {
        return new Queue(queueName, true); // durable = true 表示佇列持久化
    }
}
