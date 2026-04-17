package com.corp.esaa.corp.notificationMiddleware.messageProcessor.abstracts;

public interface IFactoryManager {

    public IMessageProcessorFactory getFactoryByType(final String type);
}
