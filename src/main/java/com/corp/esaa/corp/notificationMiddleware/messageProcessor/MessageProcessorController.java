package com.corp.esaa.corp.notificationMiddleware.messageProcessor;

import com.corp.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.corp.esaa.corp.notificationMiddleware.messageProcessor.abstracts.IFactoryWrapper;
import com.corp.esaa.corp.notificationMiddleware.messageProcessor.abstracts.IMessageProcessor;
import com.corp.esaa.corp.notificationMiddleware.messageProcessor.abstracts.IMessageProcessorFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessorController {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessorController.class);

    private final IFactoryWrapper factoryManager;
    private final ObjectMapper objectMapper;

    public MessageProcessorController(final IFactoryWrapper factoryManager, final ObjectMapper objectMapper) {
        this.factoryManager = factoryManager;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${queue.name}")
    public void processMessage(String jsonPayload) {
        logger.info("Received message: {}", jsonPayload);
        try {
            final PostMessageRequestModel requestModel = objectMapper.readValue(jsonPayload, PostMessageRequestModel.class);
            final IMessageProcessorFactory factory = factoryManager.getFactoryByType(requestModel.getNotificationType());
            if (factory != null) {
                IMessageProcessor messageProcessor = factory.getInstance();
                messageProcessor.process(requestModel);
            }
        } catch (JsonProcessingException e) {
            logger.error("Failed to deserialize message: {}", e.getMessage(), e);
        }
    }
}
