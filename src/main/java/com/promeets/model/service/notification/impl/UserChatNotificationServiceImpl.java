package com.promeets.model.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promeets.controller.StompNotificationController;
import com.promeets.model.entity.UserChat;
import com.promeets.model.service.notification.Notification;
import com.promeets.model.service.notification.UserChatNotificationService;

/**
 * Created by Vladimir on 24.04.2016.
 */
@Service
public class UserChatNotificationServiceImpl extends BaseNotificationServiceImpl<UserChat>
        implements UserChatNotificationService {

    @Autowired
    private StompNotificationController notificationController;

    @Override
    protected void onAction(UserChat entity, Notification.Action action) {
        notificationController.notifyUser(new Notification()
                        .setData(entity)
                        .setEntity(entity.getClass().getSimpleName().toLowerCase())
                        .setId(entity.getChat().getChatId())
                        .setAction(action),
                entity.getUser());
    }
}
