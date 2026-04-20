package com.corp.esaa.corp.notificationMiddleware._commons.models.api.response;

public enum CommonResponseModelEnum {

    //general
    OK(201,"OK"),
    NOTIFICATION_TYPE_NOT_SUPPORT(400,"it's not settings for the notification type"),
    FAIL_PARSING_JSON_BODY(400,"fail parsing object to json-string for publish"),
    FAIL_TO_POST_ON_MQ(500,"MQ service not available"),

    //email type
    MISSING_FIELD_SENDER_EMAIL(422, "field 'senderEmail' is required"),
    MISSING_FIELDS_RECIPIENT_EMAIL(422, "required a least one recipient,cc or bcc email to send"),
    BAD_FORMAT_EMAILS(400,"there are emails with bad format"),
    ;

    CommonResponseModelEnum(final int httpStatus, final String appMessage) {

        this.httpStatus = httpStatus;
        this.appMessage = appMessage;
    }

    private final int httpStatus;
    private final String appMessage;

    public int getHttpStatus() {
        return this.httpStatus;
    }

    public String getAppMessage() {
        return this.appMessage;
    }

}
