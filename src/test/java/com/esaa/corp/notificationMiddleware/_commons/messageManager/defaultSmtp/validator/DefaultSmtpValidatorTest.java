package com.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp.validator;

import com.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp.DefaultSmtpValidator;
import com.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModelEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


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

    @Test
    public void whenSenderHasABadFormatReturnBadFormatEnumValue() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("bad_mail@");
        requestModel.setRecipientsEmails(List.of("test@test.com"));
        final CommonResponseModelEnum response = this.defaultSmtpValidator.validate(requestModel);
        Assertions.assertEquals(CommonResponseModelEnum.BAD_FORMAT_EMAILS,response);
    }

    @Test
    public void whenAreSenderAndRecipientsReturnNull() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("sender@test.com");
        requestModel.setRecipientsEmails(List.of("test@test.com"));
        final CommonResponseModelEnum response = this.defaultSmtpValidator.validate(requestModel);
        Assertions.assertNull(response);
    }

    @Test
    public void whenThereAreNotRecipientReturnMissingRecipientEnumValue() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("test@test.com");
        final CommonResponseModelEnum response = this.defaultSmtpValidator.validate(requestModel);
        Assertions.assertEquals(CommonResponseModelEnum.MISSING_FIELDS_RECIPIENT_EMAIL,response);
    }

    @Test
    public void whenRecipientHasABadFormatReturnBadFormatEnumValue() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("test@test.com");
        requestModel.setRecipientsEmails(List.of("bad_mail@"));
        final CommonResponseModelEnum response = this.defaultSmtpValidator.validate(requestModel);
        Assertions.assertEquals(CommonResponseModelEnum.BAD_FORMAT_EMAILS,response);
    }

    @Test
    public void whenCcHasABadFormatReturnBadFormatEnumValue() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("test@test.com");
        requestModel.setCcEmails(List.of("bad_mail@"));
        final CommonResponseModelEnum response = this.defaultSmtpValidator.validate(requestModel);
        Assertions.assertEquals(CommonResponseModelEnum.BAD_FORMAT_EMAILS,response);
    }

    @Test
    public void whenBccHasABadFormatReturnBadFormatEnumValue() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("test@test.com");
        requestModel.setBccEmails(List.of("bad_mail@"));
        final CommonResponseModelEnum response = this.defaultSmtpValidator.validate(requestModel);
        Assertions.assertEquals(CommonResponseModelEnum.BAD_FORMAT_EMAILS,response);
    }

    @Test
    public void whenReplayToHasABadFormatReturnBadFormatEnumValue() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("test@test.com");
        requestModel.setRecipientsEmails(List.of("test@test.com"));
        requestModel.setReplayToEmails(List.of("bad_mail@"));
        final CommonResponseModelEnum response = this.defaultSmtpValidator.validate(requestModel);
        Assertions.assertEquals(CommonResponseModelEnum.BAD_FORMAT_EMAILS,response);
    }

    @Test
    public void whenAreReplayToGoodFormatReturnNull() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("sender@test.com");
        requestModel.setRecipientsEmails(List.of("test@test.com"));
        requestModel.setReplayToEmails(List.of("replay@test.com"));
        final CommonResponseModelEnum response = this.defaultSmtpValidator.validate(requestModel);
        Assertions.assertNull(response);
    }

    @Test
    public void whenOnlyHasCcWithoutRecipientsReturnNull() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("sender@test.com");
        requestModel.setCcEmails(List.of("cc@test.com"));
        final CommonResponseModelEnum response = this.defaultSmtpValidator.validate(requestModel);
        Assertions.assertNull(response);
    }

    @Test
    public void whenOnlyHasBccWithoutRecipientsReturnNull() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("sender@test.com");
        requestModel.setBccEmails(List.of("bcc@test.com"));
        final CommonResponseModelEnum response = this.defaultSmtpValidator.validate(requestModel);
        Assertions.assertNull(response);
    }

    @Test
    public void whenRecipientsIsEmptyButHasValidCcReturnNull() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("sender@test.com");
        requestModel.setRecipientsEmails(List.of());
        requestModel.setCcEmails(List.of("cc@test.com"));
        final CommonResponseModelEnum response = this.defaultSmtpValidator.validate(requestModel);
        Assertions.assertNull(response);
    }

    @Test
    public void whenRecipientsIsNullButHasValidBccReturnNull() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("sender@test.com");
        requestModel.setRecipientsEmails(null);
        requestModel.setBccEmails(List.of("bcc@test.com"));
        final CommonResponseModelEnum response = this.defaultSmtpValidator.validate(requestModel);
        Assertions.assertNull(response);
    }

    @Test
    public void whenSenderIsBlankReturnMissingSenderEnumValue() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("   ");
        final CommonResponseModelEnum response = this.defaultSmtpValidator.validate(requestModel);
        Assertions.assertEquals(CommonResponseModelEnum.MISSING_FIELD_SENDER_EMAIL,response);
    }

}
