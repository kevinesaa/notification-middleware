package com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.abstracts;

public interface IValidatorFactoryWrapper {
    IValidatorFactory getFactoryByType(String type);
}
