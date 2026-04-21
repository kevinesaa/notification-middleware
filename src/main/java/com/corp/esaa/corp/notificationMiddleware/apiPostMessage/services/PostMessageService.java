package com.corp.esaa.corp.notificationMiddleware.apiPostMessage.services;

import com.corp.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IInputValidator;
import com.corp.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IMessageManagerFactory;
import com.corp.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IMessageManagerFactoryWrapper;
import com.corp.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.corp.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModelEnum;
import com.corp.esaa.corp.notificationMiddleware._commons.models.domain.NotificationType;
import com.corp.esaa.corp.notificationMiddleware.apiPostMessage.messaging.IMessagePublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.stereotype.Service;

@Service
public class PostMessageService implements IPostMessageService{

    private static final Logger logger = LoggerFactory.getLogger(PostMessageService.class);

    private final IMessageManagerFactoryWrapper factoryWrapper;
    private final IMessagePublisher messagePublisher;

    public PostMessageService(final IMessageManagerFactoryWrapper factoryWrapper, final IMessagePublisher messagePublisher) {
        this.factoryWrapper = factoryWrapper;
        this.messagePublisher = messagePublisher;
    }

    @Override
    public CommonResponseModelEnum processMessage(final PostMessageRequestModel requestModel) {

        final NotificationType notificationType = NotificationType.getByKeyName(requestModel.getNotificationType());
        if( notificationType == null) {
            return CommonResponseModelEnum.NOTIFICATION_TYPE_NOT_SUPPORT;
        }

        final IMessageManagerFactory messageManagerFactory = factoryWrapper.getFactoryByType(requestModel.getNotificationType());
        final IInputValidator messageValidator = messageManagerFactory.getValidatorInstance();
        final CommonResponseModelEnum validatorResult = messageValidator.validate(requestModel);
        if( validatorResult != null ) {
            return validatorResult;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(requestModel);
            messagePublisher.publish(jsonString);
        } catch (final JsonProcessingException e) {
            logger.error("Failed to serialize message: {}", e.getMessage(), e);
            return CommonResponseModelEnum.FAIL_PARSING_JSON_BODY;
        }
        catch (final AmqpException  e) {
            logger.error("Fail to connect with MQ: {}", e.getMessage(), e);
            return CommonResponseModelEnum.FAIL_TO_POST_ON_MQ;
        }

        return CommonResponseModelEnum.OK;
    }
}
