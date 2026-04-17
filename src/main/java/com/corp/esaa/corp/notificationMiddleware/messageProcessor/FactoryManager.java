package com.corp.esaa.corp.notificationMiddleware.messageProcessor;

import com.corp.esaa.corp.notificationMiddleware.messageProcessor.abstracts.IFactoryManager;
import com.corp.esaa.corp.notificationMiddleware.messageProcessor.abstracts.IMessageProcessorFactory;
import com.corp.esaa.corp.notificationMiddleware.messageProcessor.factories.DefaultSmtpProcessorFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FactoryManager implements IFactoryManager {

    private static Map<String,IMessageProcessorFactory> factoriesMap;

    public IMessageProcessorFactory getFactoryByType(final String type) {
        if(factoriesMap == null) {
            synchronized (FactoryManager.class) {
                if(factoriesMap == null) {
                    initFactories();
                }
            }
        }
        return  factoriesMap.get(type.toLowerCase());
    }

    private  void initFactories() {
        factoriesMap = new HashMap<>();
        factoriesMap.put("smtp",new DefaultSmtpProcessorFactory());
    }
}
