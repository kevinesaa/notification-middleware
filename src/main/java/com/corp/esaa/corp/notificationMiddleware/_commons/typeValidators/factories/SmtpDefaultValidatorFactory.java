package com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.factories;

import com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.abstracts.IInputValidator;
import com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.abstracts.IValidatorFactory;
import com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.validators.SmtpDefaultValidator;

public class SmtpDefaultValidatorFactory implements IValidatorFactory {

    @Override
    public IInputValidator getInstance() {
        return new SmtpDefaultValidator();
    }
}
