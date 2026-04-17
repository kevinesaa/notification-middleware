package com.corp.esaa.corp.notificationMiddleware.apiPostMessage.services;

import com.corp.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.corp.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModel;
import com.corp.esaa.corp.notificationMiddleware.apiPostMessage.controllers.MessagePublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.AmqpException;
import org.springframework.stereotype.Component;

@Component
public class PostMessageService {

    private final MessagePublisher messagePublisher;

    public PostMessageService(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    public CommonResponseModel processMessage(PostMessageRequestModel requestModel) {

        final CommonResponseModel response = new CommonResponseModel();
        response.setAppCodeName("OK");
        response.setAppMessage("OK");
        try {
            messagePublisher.publish(requestModel);
        } catch (AmqpException | JsonProcessingException e) {

            response.setAppCodeName("FAIL_TO_POST_ON_MQ");
            response.setAppMessage(null);
        }

        return response;
    }
}
