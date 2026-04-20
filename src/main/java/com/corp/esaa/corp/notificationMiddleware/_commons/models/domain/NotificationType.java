package com.corp.esaa.corp.notificationMiddleware._commons.models.domain;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum NotificationType {

    SMTP("smtp"),
    ;

    private static ConcurrentMap<String,NotificationType> notificationTypeMap;
    private final String keyName;

    NotificationType(final String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }

    public static NotificationType getByKeyName(final String keyName) {
        if(notificationTypeMap == null) {
            synchronized (NotificationType.class) {
                if(notificationTypeMap == null) {
                    initNotificationMap();
                }
            }
        }

        return NotificationType.notificationTypeMap.get(keyName);
    }

    private static void initNotificationMap() {
        notificationTypeMap = new ConcurrentHashMap<>();
        for (NotificationType item : NotificationType.values()) {
            notificationTypeMap.put(item.getKeyName(),item);
        }
    }
}
