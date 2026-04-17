package com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.validators;

import com.corp.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.corp.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModelEnum;
import com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.abstracts.IInputValidator;

public class SmtpDefaultValidator implements IInputValidator {


    @Override
    public CommonResponseModelEnum validate(PostMessageRequestModel requestModel) {
        return null;
    }
}
