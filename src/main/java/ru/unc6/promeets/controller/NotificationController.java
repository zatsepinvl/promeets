package ru.unc6.promeets.controller;

import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.service.notification.Notification;

/**
 * Created by Vladimir on 30.05.2016.
 */
public interface NotificationController {
    void notifyUser(Notification notification, User user);
}
