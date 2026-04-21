package com.corp.esaa.corp.notificationMiddleware._commons.messageManager.abstracts;

public interface IMessageManagerFactoryWrapper {

    public IMessageManagerFactory getFactoryByType(final String messageType);

}
