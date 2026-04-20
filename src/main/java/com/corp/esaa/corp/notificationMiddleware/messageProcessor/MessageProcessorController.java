package com.corp.esaa.corp.notificationMiddleware.messageProcessor;

import com.corp.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.corp.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModelEnum;
import com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.abstracts.IInputValidator;
import com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.abstracts.IValidatorFactory;
import com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.abstracts.IValidatorFactoryWrapper;
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
    private final IValidatorFactoryWrapper validatorFactoryWrapper;
    private final ObjectMapper objectMapper;

    public MessageProcessorController(final IFactoryWrapper factoryManager, final IValidatorFactoryWrapper validatorFactoryWrapper,final ObjectMapper objectMapper) {
        this.factoryManager = factoryManager;
        this.validatorFactoryWrapper = validatorFactoryWrapper;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${queue.name}")
    public void processMessage(String jsonPayload) {
        logger.info("Received message: {}", jsonPayload);
        try {

            final PostMessageRequestModel requestModel = objectMapper.readValue(jsonPayload, PostMessageRequestModel.class);
            final IValidatorFactory validatorFactory = validatorFactoryWrapper.getFactoryByType(requestModel.getNotificationType());

            if (validatorFactory == null) {
                logger.warn("The message type is not supported: {}",requestModel.getNotificationType());
            }
            else {

                final IInputValidator inputValidator = validatorFactory.getInstance();
                final CommonResponseModelEnum response = inputValidator.validate(requestModel);
                if(response != null) {
                    logger.warn("not valid notification: {}",response.getAppMessage());
                }
                else {

                    final IMessageProcessorFactory processorFactory = factoryManager.getFactoryByType(requestModel.getNotificationType());
                    final IMessageProcessor messageProcessor = processorFactory.getInstance();
                    messageProcessor.process(requestModel);
                }

            }
        } catch (JsonProcessingException e) {
            logger.error("Failed to deserialize message: {}", e.getMessage(), e);
        }
    }
}
