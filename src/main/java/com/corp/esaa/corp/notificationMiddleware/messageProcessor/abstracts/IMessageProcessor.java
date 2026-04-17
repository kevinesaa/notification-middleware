package com.corp.esaa.corp.notificationMiddleware.messageProcessor.abstracts;

import com.corp.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;

public interface IMessageProcessor {

    public void process(final PostMessageRequestModel requestModel);
}
