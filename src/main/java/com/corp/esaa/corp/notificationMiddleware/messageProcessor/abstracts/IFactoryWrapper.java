package com.corp.esaa.corp.notificationMiddleware.messageProcessor.abstracts;

public interface IFactoryWrapper {

    public IMessageProcessorFactory getFactoryByType(final String type);
}
