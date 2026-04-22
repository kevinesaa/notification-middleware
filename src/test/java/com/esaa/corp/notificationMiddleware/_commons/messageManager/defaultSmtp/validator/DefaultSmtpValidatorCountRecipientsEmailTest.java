package com.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp.validator;

import com.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp.DefaultSmtpValidator;
import com.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


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

    @Test
    public void whenThereIsSenderReturnZero(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("test@test.com");
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(0,count);
    }

    @Test
    public void whenThereAreAEmptyListOfRecipientsReturnZero(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setRecipientsEmails(List.of());
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(0,count);
    }

    @Test
    public void whenThereAreAEmptyListOfCcReturnZero(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setCcEmails(List.of());
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(0,count);
    }

    @Test
    public void whenThereAreAEmptyListOfBccReturnZero(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setBccEmails(List.of());
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(0,count);
    }

    @Test
    public void whenThereAreEmptyRecipientsReturnZero(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setRecipientsEmails(List.of(""));
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(0,count);
    }

    @Test
    public void whenThereAreEmptyCcReturnZero(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setCcEmails(List.of(""));
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(0,count);
    }

    @Test
    public void whenThereAreEmptyBccReturnZero(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setBccEmails(List.of(""));
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(0,count);
    }

    @Test
    public void whenThereIsRecipientsReturnOne(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setRecipientsEmails(List.of("test@test.com"));
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(1,count);
    }
    
    @Test
    public void whenThereIsCcReturnOne(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setCcEmails(List.of("test@test.com"));
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(1,count);
    }
    
    @Test
    public void whenThereIsBccReturnOne(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setBccEmails(List.of("test@test.com"));
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(1,count);
    }
    
    @Test
    public void whenThereIsSenderAndRecipientsReturnOne(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("test@test.com");
        requestModel.setRecipientsEmails(List.of("test@test.com"));
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(1,count);
    }
    
    @Test
    public void whenThereIsSenderAndCcReturnOne(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("test@test.com");
        requestModel.setCcEmails(List.of("test@test.com"));
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(1,count);
    }
    
    @Test
    public void whenThereIsSenderAndBccReturnOne(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("test@test.com");
        requestModel.setBccEmails(List.of("test@test.com"));
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(1,count);
    }
    
    @Test
    public void whenThereIsRecipientsAndCcReturnTwo(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setRecipientsEmails(List.of("test@test.com"));
        requestModel.setCcEmails(List.of("test@test.com"));
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(2,count);
    }

    @Test
    public void whenThereIsRecipientsAndBccReturnTwo(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setRecipientsEmails(List.of("test@test.com"));
        requestModel.setBccEmails(List.of("test@test.com"));
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(2,count);
    }

    @Test
    public void whenThereIsCcAndBccReturnTwo(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setCcEmails(List.of("test@test.com"));
        requestModel.setBccEmails(List.of("test@test.com"));
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(2,count);
    }

    @Test
    public void whenThereAreSenderAndRecipientsAndCcAndBccReturnThree(){
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("test@test.com");
        requestModel.setRecipientsEmails(List.of("test@test.com"));
        requestModel.setCcEmails(List.of("test@test.com"));
        requestModel.setBccEmails(List.of("test@test.com"));
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(3,count);
    }

    @Test
    public void whenThereAreRecipientsAndCcAndBccReturnThree(){
        
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setRecipientsEmails(List.of("test@test.com"));
        requestModel.setCcEmails(List.of("test@test.com"));
        requestModel.setBccEmails(List.of("test@test.com"));
        long count = this.defaultSmtpValidator.countAllNotEmptyRecipients(requestModel);
        Assertions.assertEquals(3,count);
    }
    
}
