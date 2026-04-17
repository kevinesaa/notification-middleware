package com.corp.esaa.corp.notificationMiddleware.healthStatus;

import com.corp.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping({"healthStatus","healthStatus/"})
    public ResponseEntity<CommonResponseModel> healthStatus() {
        CommonResponseModel responseModel = new CommonResponseModel();
        responseModel.setAppCodeName("OK");
        responseModel.setAppMessage("OK");
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }
}
