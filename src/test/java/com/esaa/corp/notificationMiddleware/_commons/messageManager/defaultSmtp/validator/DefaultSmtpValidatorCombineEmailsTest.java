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

    @Test
    public void whenThereIsSenderCombineContainsIt() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("test@test.com");
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertTrue(emailList.contains("test@test.com"));
    }

    @Test
    public void whenThereIsReplayToCombineContainsIt() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setReplayToEmails(List.of("test@test.com"));
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertTrue(emailList.contains("test@test.com"));
    }

    @Test
    public void whenThereIsRecipientsCombineContainsIt() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setRecipientsEmails(List.of("test@test.com"));
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertTrue(emailList.contains("test@test.com"));
    }

    @Test
    public void whenThereIsCcCombineContainsIt() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setCcEmails(List.of("test@test.com"));
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertTrue(emailList.contains("test@test.com"));
    }
    
    @Test
    public void whenThereIsBccCombineContainsIt() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setBccEmails(List.of("test@test.com"));
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertTrue(emailList.contains("test@test.com"));
    }

    @Test
    public void whenSenderIsEmptyReturnAnEmptyList() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail("");
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertTrue(emailList.isEmpty());
    }

    @Test
    public void whenReplayToIsEmptyReturnAnEmptyList() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setReplayToEmails(List.of(""));
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertTrue(emailList.isEmpty());
    }

    @Test
    public void whenRecipientsIsEmptyReturnAnEmptyList() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setRecipientsEmails(List.of(""));
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertTrue(emailList.isEmpty());
    }

    @Test
    public void whenCcIsEmptyReturnAnEmptyList() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setCcEmails(List.of(""));
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertTrue(emailList.isEmpty());
    }
    
    @Test
    public void whenBccIsEmptyReturnAnEmptyList() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setBccEmails(List.of(""));
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertTrue(emailList.isEmpty());
    }

    @Test
    public void whenSenderIsWhiteSpaceReturnAnEmptyList() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setSenderEmail(" ");
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertTrue(emailList.isEmpty());
    }

    @Test
    public void whenReplayToIsWhiteSpaceReturnAnEmptyList() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setReplayToEmails(List.of(" "));
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertTrue(emailList.isEmpty());
    }

    @Test
    public void whenRecipientsIsWhiteSpaceReturnAnEmptyList() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setRecipientsEmails(List.of(" "));
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertTrue(emailList.isEmpty());
    }
    
    @Test
    public void whenCcIsWhiteSpaceReturnAnEmptyList() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setCcEmails(List.of(" "));
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertTrue(emailList.isEmpty());
    }

    @Test
    public void whenBccIsWhiteSpaceReturnAnEmptyList() {
        final PostMessageRequestModel requestModel = new PostMessageRequestModel();
        requestModel.setBccEmails(List.of(" "));
        List<String> emailList = this.defaultSmtpValidator.combineAllEmails(requestModel);
        Assertions.assertTrue(emailList.isEmpty());
    }

    
}
