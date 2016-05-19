package ru.unc6.promeets.model.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.controller.NotificationController;
import ru.unc6.promeets.model.entity.UserChat;
import ru.unc6.promeets.model.service.notification.Notification;
import ru.unc6.promeets.model.service.notification.UserChatNotificationService;

/**
 * Created by Vladimir on 24.04.2016.
 */
@Service
public class UserChatNotificationServiceImpl extends BaseNotificationServiceImpl<UserChat>
        implements UserChatNotificationService {

    @Autowired
    private NotificationController notificationController;

    @Override
    protected void onAction(UserChat entity, Notification.Action action) {
        notificationController.sendNotificationToUser(new Notification()
                        .setData(entity)
                        .setEntity(entity.getClass().getSimpleName().toLowerCase())
                        .setId(entity.getChat().getChatId())
                        .setAction(action),
                entity.getUser());
    }
}
