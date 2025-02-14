package com.example.demoservice.mq;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name.log}")
    private String logQueueName;  // 從 application.properties 讀取佇列名稱

    @Value("${rabbitmq.queue.name.event}")
    private String eventQueueName;

    @Bean
    public Queue logQueue() {
        return new Queue(logQueueName, true); // durable = true 表示佇列持久化
    }

    @Bean
    public Queue eventQueue() {
        return new Queue(eventQueueName, false);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper());
        return converter;
    }
    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("java.util.HashMap");
        classMapper.setTrustedPackages("com.example.demoservice.model");
        return classMapper;
    }
}
