package com.esaa.corp.notificationMiddleware.healthStatus;

import com.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModelEnum;
import com.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping({"healthStatus","healthStatus/"})
    public ResponseEntity<CommonResponseModel> healthStatus() {

        final CommonResponseModel
                responseModel = new CommonResponseModel(CommonResponseModelEnum.OK);

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }
}
