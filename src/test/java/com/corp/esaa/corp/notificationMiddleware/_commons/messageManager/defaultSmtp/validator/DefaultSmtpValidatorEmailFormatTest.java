package com.corp.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp.validator;

import com.corp.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp.DefaultSmtpValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DefaultSmtpValidatorEmailFormatTest {

    private DefaultSmtpValidator defaultSmtpValidator;

    @BeforeEach
    public void setup() {
        this.defaultSmtpValidator = new DefaultSmtpValidator();
    }


    @Test
    public void whenThereBasicFormatReturnTrue() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("test@test.com");
        Assertions.assertTrue(isValidFormat);
    }

}
