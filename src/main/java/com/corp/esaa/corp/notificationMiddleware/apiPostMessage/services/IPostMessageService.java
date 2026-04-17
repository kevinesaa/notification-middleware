package com.corp.esaa.corp.notificationMiddleware.apiPostMessage.services;

import com.corp.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.corp.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModelEnum;

public interface IPostMessageService {

    public CommonResponseModelEnum processMessage(final PostMessageRequestModel requestModel);
}
