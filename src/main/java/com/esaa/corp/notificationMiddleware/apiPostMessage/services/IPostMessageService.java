package com.esaa.corp.notificationMiddleware.apiPostMessage.services;

import com.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModelEnum;
import com.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;

public interface IPostMessageService {

    public CommonResponseModelEnum processMessage(final PostMessageRequestModel requestModel);
}
