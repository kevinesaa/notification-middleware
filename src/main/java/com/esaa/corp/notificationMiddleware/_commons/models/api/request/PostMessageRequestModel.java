package com.esaa.corp.notificationMiddleware._commons.models.api.request;

import java.util.List;

public class PostMessageRequestModel {

    private String notificationType;

    private String senderEmail;
    private List<String> recipientsEmails;

    private String subject;
    private String messageBody;
    private String htmlMessage;

    private List<String> ccEmails;
    private List<String> bccEmails;
    private List<String> replayToEmails;
    private List<String> attachments;



    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getNotificationType() {
        return notificationType == null?null:notificationType.toLowerCase();
    }

    public void setNotificationType(final String notificationType) {
        this.notificationType = notificationType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getHtmlMessage() {
        return htmlMessage;
    }

    public void setHtmlMessage(String htmlMessage) {
        this.htmlMessage = htmlMessage;
    }

    public String getSenderEmail() {
        return senderEmail == null ? null : senderEmail.toLowerCase().trim();
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public List<String> getRecipientsEmails() {
        return recipientsEmails;
    }

    public void setRecipientsEmails(List<String> recipientsEmails) {
        this.recipientsEmails = recipientsEmails;
    }

    public List<String> getCcEmails() {
        return ccEmails;
    }

    public void setCcEmails(List<String> ccEmails) {
        this.ccEmails = ccEmails;
    }

    public List<String> getBccEmails() {
        return bccEmails;
    }

    public void setBccEmails(List<String> bccEmails) {
        this.bccEmails = bccEmails;
    }

    public List<String> getReplayToEmails() {
        return replayToEmails;
    }

    public void setReplayToEmails(List<String> replayToEmails) {
        this.replayToEmails = replayToEmails;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }
}
