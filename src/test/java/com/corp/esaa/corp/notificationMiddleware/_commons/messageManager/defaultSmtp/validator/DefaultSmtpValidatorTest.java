package com.corp.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp.validator;

import com.corp.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp.DefaultSmtpValidator;
import com.corp.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.corp.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModelEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DefaultSmtpValidatorTest {

    private DefaultSmtpValidator defaultSmtpValidator;

    @BeforeEach
    public void setup() {
        this.defaultSmtpValidator = new DefaultSmtpValidator();
    }


    @Test
    public void whenSenderIsEmptyReturnMissingSenderEnumValue() {

        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        final CommonResponseModelEnum response = this.defaultSmtpValidator.validate(requestModel);
        Assertions.assertEquals(CommonResponseModelEnum.MISSING_FIELD_SENDER_EMAIL,response);
    }

}
