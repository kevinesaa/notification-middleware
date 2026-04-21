package com.corp.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp;

import com.corp.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IMessageProcessor;
import com.corp.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;

public class DefaultSmtpProcessor implements IMessageProcessor {

    @Override
    public void process(final PostMessageRequestModel requestModel) {
        System.out.println("aqui procesamos: "+requestModel.getMessageBody());
    }
}
