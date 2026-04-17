package com.corp.esaa.corp.notificationMiddleware.messageProcessor.processors;

import com.corp.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.corp.esaa.corp.notificationMiddleware.messageProcessor.abstracts.IMessageProcessor;

public class DefaultSmtpProcessor implements IMessageProcessor {

    @Override
    public void process(final PostMessageRequestModel requestModel) {
        System.out.println("aqui procesamos: "+requestModel.getMessageBody());
    }
}
