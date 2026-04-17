package com.corp.esaa.corp.notificationMiddleware.messageProcessor.factories;

import com.corp.esaa.corp.notificationMiddleware.messageProcessor.abstracts.IMessageProcessor;
import com.corp.esaa.corp.notificationMiddleware.messageProcessor.abstracts.IMessageProcessorFactory;
import com.corp.esaa.corp.notificationMiddleware.messageProcessor.processors.DefaultSmtpProcessor;

public class DefaultSmtpProcessorFactory implements IMessageProcessorFactory {

    @Override
    public IMessageProcessor getInstance() {
        return new DefaultSmtpProcessor();
    }
}
