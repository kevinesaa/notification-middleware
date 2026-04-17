package com.corp.esaa.corp.notificationMiddleware.messageProcessor;

import com.corp.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.corp.esaa.corp.notificationMiddleware.messageProcessor.abstracts.IFactoryWrapper;
import com.corp.esaa.corp.notificationMiddleware.messageProcessor.abstracts.IMessageProcessor;
import com.corp.esaa.corp.notificationMiddleware.messageProcessor.abstracts.IMessageProcessorFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessorController {

    private final IFactoryWrapper factoryManager;
    private final ObjectMapper objectMapper;

    public MessageProcessorController(final IFactoryWrapper factoryManager, final ObjectMapper objectMapper) {
        this.factoryManager = factoryManager;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${queue.name}")
    public void processMessage(String jsonPayload) {
        System.out.println(jsonPayload);
        try {
            final PostMessageRequestModel requestModel = objectMapper.readValue(jsonPayload, PostMessageRequestModel.class);
            final IMessageProcessorFactory factory = factoryManager.getFactoryByType(requestModel.getNotificationType());
            if (factory != null) {
                IMessageProcessor messageProcessor = factory.getInstance();
                messageProcessor.process(requestModel);
            }
        } catch (JsonProcessingException e) {
            System.err.println("Failed to deserialize message: " + e.getMessage());
        }
    }
}
