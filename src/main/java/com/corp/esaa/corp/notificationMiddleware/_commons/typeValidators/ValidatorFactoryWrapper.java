package com.corp.esaa.corp.notificationMiddleware._commons.typeValidators;

import com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.abstracts.IValidatorFactory;
import com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.abstracts.IValidatorFactoryWrapper;
import com.corp.esaa.corp.notificationMiddleware._commons.typeValidators.factories.SmtpDefaultValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValidatorFactoryWrapper implements IValidatorFactoryWrapper {

    private static Map<String,IValidatorFactory> factoriesMap;

    @Override
    public IValidatorFactory getFactoryByType(final String type) {
        if(factoriesMap == null) {
            synchronized (ValidatorFactoryWrapper.class) {
                if(factoriesMap == null) {
                    initFactories();
                }
            }
        }
        return  factoriesMap.get(type.toLowerCase());
    }

    private void initFactories() {
        factoriesMap = new HashMap<>();
        factoriesMap.put("smtp",new SmtpDefaultValidatorFactory());
    }
}
