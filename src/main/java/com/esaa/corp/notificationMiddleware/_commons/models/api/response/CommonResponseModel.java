package com.esaa.corp.notificationMiddleware._commons.models.api.response;

public class CommonResponseModel {

    private final String appCodeName;
    private final String appMessage;

    public CommonResponseModel(final CommonResponseModelEnum v) {
        this.appCodeName = v.name();
        this.appMessage = v.getAppMessage();
    }

    public CommonResponseModel(final String appCodeName,final String appMessage) {
        this.appCodeName = appCodeName;
        this.appMessage = appMessage;
    }

    public String getAppCodeName() {
        return this.appCodeName;
    }

    public String getAppMessage() {
        return this.appMessage;
    }
}
