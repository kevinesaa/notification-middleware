package com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.abstracts;

import com.corp.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.corp.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModelEnum;

public interface IInputValidator {


    public CommonResponseModelEnum validate(final PostMessageRequestModel requestModel);
}
