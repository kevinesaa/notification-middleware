package com.esaa.corp.notificationMiddleware._commons.messageManager.abstracts;

import com.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;

public interface IMessageProcessor {

    public void process(final PostMessageRequestModel requestModel);
}
