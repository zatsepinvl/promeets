package com.promeets.controller;

import com.promeets.model.entity.User;
import com.promeets.model.service.notification.Notification;

/**
 * Created by Vladimir on 30.05.2016.
 */
public interface NotificationController {
    void notifyUser(Notification notification, User user);
}
