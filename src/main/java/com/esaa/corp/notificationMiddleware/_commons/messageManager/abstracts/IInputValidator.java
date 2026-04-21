package com.esaa.corp.notificationMiddleware._commons.messageManager.abstracts;

import com.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModelEnum;

public interface IInputValidator {


    public CommonResponseModelEnum validate(final PostMessageRequestModel requestModel);
}
