package com.example.demoservice.mq;

import com.example.demoservice.model.LogMessageQueueModel;
import com.example.demoservice.service.service.LogService;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    @Resource
    private LogService logService;

    @RabbitListener(queues = "${rabbitmq.queue.name.event}")
    public void receiveEventMessage(String message) {
        System.out.println("📥 接收 Event 消息：" + message);
    }

    @RabbitListener(queues = "${rabbitmq.queue.name.log}") // 監聽佇列
    public void receiveMessage(LogMessageQueueModel message) {
        System.out.println("📥 接收消息(LogModel)：" + message);
        logService.saveAuthLog(message);
    }

}

