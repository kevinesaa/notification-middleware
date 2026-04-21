package com.corp.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp;

import com.corp.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IInputValidator;
import com.corp.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IMessageManagerFactory;
import com.corp.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IMessageProcessor;

public class DefaultSmtpFactory implements IMessageManagerFactory {

    @Override
    public IInputValidator getValidatorInstance() {
        return new DefaultSmtpValidator();
    }

    @Override
    public IMessageProcessor getProcessorInstance() {
        return new DefaultSmtpProcessor();
    }

}
