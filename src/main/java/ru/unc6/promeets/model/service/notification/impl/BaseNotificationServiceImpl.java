package ru.unc6.promeets.model.service.notification.impl;

import org.springframework.scheduling.annotation.Async;
import ru.unc6.promeets.model.service.notification.BaseNotificationService;
import ru.unc6.promeets.model.service.notification.Notification;

public abstract class BaseNotificationServiceImpl<T> implements BaseNotificationService<T> {

    @Async
    @Override
    public void onCreate(T entity) {
        onAction(entity, Notification.Action.CREATE);
    }

    @Async
    @Override
    public void onUpdate(T entity) {
        onAction(entity, Notification.Action.UPDATE);
    }

    @Async
    @Override
    public void onDelete(T entity) {
        onAction(entity, Notification.Action.DELETE);
    }

    protected abstract void onAction(T entity, Notification.Action action);
}
