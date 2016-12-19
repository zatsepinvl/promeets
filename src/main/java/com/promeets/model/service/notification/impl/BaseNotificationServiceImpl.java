package com.promeets.model.service.notification.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.promeets.model.service.notification.BaseNotificationService;
import com.promeets.model.service.notification.Notification;

@Service
public abstract class BaseNotificationServiceImpl<T> implements BaseNotificationService<T> {

    @Override
    public void onCreate(T entity) {
        onAction(entity, Notification.Action.CREATE);
    }

    @Override
    public void onUpdate(T entity) {
        onAction(entity, Notification.Action.UPDATE);
    }


    @Override
    public void onDelete(T entity) {
        onAction(entity, Notification.Action.DELETE);
    }

    @Async
    protected abstract void onAction(T entity, Notification.Action action);
}
