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

    @RabbitListener(queues = "${rabbitmq.queue.name.log}") // 監聽佇列
    public void receiveMessage(Object message) {
        if (message instanceof String) {
            System.out.println("📥 接收消息：" + message);
        } else if (message instanceof LogMessageQueueModel) {
            logService.saveLoginLog((LogMessageQueueModel) message);
            System.out.println("📥 接收消息 model：" + ((LogMessageQueueModel) message).getAccount());
        }
    }

}

