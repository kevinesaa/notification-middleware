package com.corp.esaa.corp.notificationMiddleware.messageProcessor;

import com.corp.esaa.corp.notificationMiddleware._commons.models.domain.NotificationType;
import com.corp.esaa.corp.notificationMiddleware.messageProcessor.abstracts.IFactoryWrapper;
import com.corp.esaa.corp.notificationMiddleware.messageProcessor.abstracts.IMessageProcessorFactory;
import com.corp.esaa.corp.notificationMiddleware.messageProcessor.factories.DefaultSmtpProcessorFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class FactoryWrapper implements IFactoryWrapper {

    private static ConcurrentMap<String,IMessageProcessorFactory> factoriesMap;

    public IMessageProcessorFactory getFactoryByType(final String type) {
        if(factoriesMap == null) {
            synchronized (FactoryWrapper.class) {
                if(factoriesMap == null) {
                    initFactories();
                }
            }
        }
        return  factoriesMap.get(type.toLowerCase());
    }

    private  void initFactories() {
        factoriesMap = new ConcurrentHashMap<>();
        factoriesMap.put(NotificationType.SMTP.getKeyName(), new DefaultSmtpProcessorFactory());
    }
}
