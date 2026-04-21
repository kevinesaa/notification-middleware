package com.esaa.corp.notificationMiddleware._commons.messageManager.abstracts;

public interface IMessageManagerFactory {

    public IInputValidator getValidatorInstance();

    public IMessageProcessor getProcessorInstance();
}
