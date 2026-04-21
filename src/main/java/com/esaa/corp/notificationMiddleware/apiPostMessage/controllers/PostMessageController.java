package com.esaa.corp.notificationMiddleware.apiPostMessage.controllers;

import com.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModelEnum;
import com.esaa.corp.notificationMiddleware.apiPostMessage.services.IPostMessageService;
import com.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostMessageController {


    private final IPostMessageService service;

    public PostMessageController(final IPostMessageService service) {
        this.service = service;
    }

    @PostMapping({"post-message","post-message/"})
    public ResponseEntity<CommonResponseModel> postMessage(@RequestBody final PostMessageRequestModel requestModel) {

        final CommonResponseModelEnum status = service.processMessage(requestModel);
        final CommonResponseModel responseBody = new CommonResponseModel(status);
        return ResponseEntity.status(status.getHttpStatus()).body(responseBody);
    }
}
