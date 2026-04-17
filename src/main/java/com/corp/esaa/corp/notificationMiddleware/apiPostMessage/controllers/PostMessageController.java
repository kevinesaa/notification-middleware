package com.corp.esaa.corp.notificationMiddleware.apiPostMessage.controllers;

import com.corp.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.corp.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModel;
import com.corp.esaa.corp.notificationMiddleware.apiPostMessage.services.PostMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostMessageController {


    private final PostMessageService service;

    public PostMessageController(PostMessageService service) {
        this.service = service;
    }

    @PostMapping({"post-message","post-message/"})
    public ResponseEntity<CommonResponseModel> postMessage(@RequestBody PostMessageRequestModel requestModel) {

        CommonResponseModel response = service.processMessage(requestModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
