package com.esaa.corp.notificationMiddleware._commons.messageManager;

import com.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IMessageManagerFactory;
import com.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IMessageManagerFactoryWrapper;
import com.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp.DefaultSmtpFactory;
import com.esaa.corp.notificationMiddleware._commons.models.domain.NotificationType;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class MessageManagerWrapper implements IMessageManagerFactoryWrapper {

    private static ConcurrentMap<String, IMessageManagerFactory> factoriesMap;


    @Override
    public IMessageManagerFactory getFactoryByType(final String messageType) {
        if(factoriesMap == null) {
            synchronized (MessageManagerWrapper.class) {
                if(factoriesMap == null) {
                    initFactories();
                }
            }
        }
        return  factoriesMap.get(messageType.toLowerCase());
    }

    private  void initFactories() {
        factoriesMap = new ConcurrentHashMap<>();
        factoriesMap.put(NotificationType.SMTP.getKeyName(), new DefaultSmtpFactory());
    }
}
