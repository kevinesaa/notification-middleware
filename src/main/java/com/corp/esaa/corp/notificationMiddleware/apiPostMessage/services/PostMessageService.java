package com.corp.esaa.corp.notificationMiddleware.apiPostMessage.services;

import com.corp.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.corp.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModelEnum;
import com.corp.esaa.corp.notificationMiddleware._commons.models.domain.NotificationType;
import com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.abstracts.IInputValidator;
import com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.abstracts.IValidatorFactory;
import com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.abstracts.IValidatorFactoryWrapper;
import com.corp.esaa.corp.notificationMiddleware.apiPostMessage.messaging.IMessagePublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.AmqpException;
import org.springframework.stereotype.Component;

@Component
public class PostMessageService implements IPostMessageService{

    private final IValidatorFactoryWrapper validatorFactoryWrapper;
    private final IMessagePublisher messagePublisher;

    public PostMessageService(IValidatorFactoryWrapper validatorFactoryWrapper, IMessagePublisher messagePublisher) {
        this.validatorFactoryWrapper = validatorFactoryWrapper;
        this.messagePublisher = messagePublisher;
    }

    @Override
    public CommonResponseModelEnum processMessage(final PostMessageRequestModel requestModel) {

        final NotificationType notificationType = NotificationType.getByKeyName(requestModel.getNotificationType());
        if( notificationType == null) {
            return CommonResponseModelEnum.NOTIFICATION_TYPE_NOT_SUPPORT;
        }

        final IValidatorFactory validatorFactory = validatorFactoryWrapper.getFactoryByType(requestModel.getNotificationType());
        final IInputValidator messageValidator = validatorFactory.getInstance();
        final CommonResponseModelEnum validatorResult = messageValidator.validate(requestModel);
        if( validatorResult != null ) {
            return validatorResult;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(requestModel);
            messagePublisher.publish(jsonString);
        } catch (final JsonProcessingException e) {
            return CommonResponseModelEnum.FAIL_PARSING_JSON_BODY;
        }
        catch (final AmqpException  e) {
            return CommonResponseModelEnum.FAIL_TO_POST_ON_MQ;
        }

        return CommonResponseModelEnum.OK;
    }
}
