package com.esaa.corp.notificationMiddleware.messageProcessor;

import com.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IMessageProcessor;
import com.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModelEnum;
import com.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IInputValidator;
import com.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IMessageManagerFactory;
import com.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IMessageManagerFactoryWrapper;
import com.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessorController {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessorController.class);


    private final ObjectMapper objectMapper;
    private final IMessageManagerFactoryWrapper factoryWrapper;

    public MessageProcessorController(final ObjectMapper objectMapper, final IMessageManagerFactoryWrapper factoryWrapper) {
        this.objectMapper = objectMapper;
        this.factoryWrapper = factoryWrapper;
    }


    @RabbitListener(queues = "${queue.name}")
    public void processMessage(final String jsonPayload) {

        logger.info("Received message: {}", jsonPayload);
        try {

            final PostMessageRequestModel requestModel = objectMapper.readValue(jsonPayload, PostMessageRequestModel.class);
            final IMessageManagerFactory messageManagerFactory = factoryWrapper.getFactoryByType(requestModel.getNotificationType());

            if (messageManagerFactory == null) {
                logger.warn("The message type is not supported: {}",requestModel.getNotificationType());
            }
            else {

                final IInputValidator inputValidator = messageManagerFactory.getValidatorInstance();
                final CommonResponseModelEnum response = inputValidator.validate(requestModel);
                if(response != null) {
                    logger.warn("not valid notification: {}",response.getAppMessage());
                }
                else {

                    final IMessageProcessor messageProcessor = messageManagerFactory.getProcessorInstance();
                    messageProcessor.process(requestModel);
                }

            }
        } catch (JsonProcessingException e) {
            logger.error("Failed to deserialize message: {}", e.getMessage(), e);
        }
    }
}
