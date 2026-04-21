package com.corp.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp.validator;

import com.corp.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp.DefaultSmtpValidator;
import com.corp.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DefaultSmtpValidatorCountRecipientsEmailTest {

    private DefaultSmtpValidator defaultSmtpValidator;

    @BeforeEach
    public void setup() {
        this.defaultSmtpValidator = new DefaultSmtpValidator();
    }


    @Test
    public void whenThereAreNotRecipientCountReturnZero() {

        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(0,count);
    }

}
