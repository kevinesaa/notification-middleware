package com.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp.validator;

import com.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp.DefaultSmtpValidator;
import com.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class DefaultSmtpValidatorCombineEmailsTest {

    private DefaultSmtpValidator defaultSmtpValidator;

    @BeforeEach
    public void setup() {
        this.defaultSmtpValidator = new DefaultSmtpValidator();
    }

    @Test
    public void whenThereAreNotEmailCombineReturnNotNullList() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertNotNull(emailList);
    }

    @Test
    public void whenThereAreNotEmailCombineReturnAnEmptyList() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertTrue(emailList.isEmpty());
    }
}
