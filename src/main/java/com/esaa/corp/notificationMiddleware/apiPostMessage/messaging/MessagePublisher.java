package com.esaa.corp.notificationMiddleware.apiPostMessage.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher implements IMessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${exchange.name}")
    private String exchange;

    @Value("${routing.key}")
    private String routingKey;

    public MessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(final String message)  {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
