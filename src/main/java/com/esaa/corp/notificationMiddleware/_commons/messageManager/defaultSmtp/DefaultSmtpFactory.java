package com.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp;

import com.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IMessageProcessor;
import com.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IInputValidator;
import com.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IMessageManagerFactory;

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
