package ru.unc6.promeets.model.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.controller.AppSTOMPController;
import ru.unc6.promeets.model.entity.UserMessage;
import ru.unc6.promeets.model.service.notification.Notification;

/**
 * Created by Vladimir on 19.04.2016.
 */
@Service
public class UserMessageNotificationService extends BaseNotificationServiceImpl<UserMessage> {
    @Autowired
    private AppSTOMPController stompController;

    @Override
    protected void onAction(UserMessage entity, Notification.Action action) {
        stompController.sendNotificationToUser(new Notification()
                        .setData(entity)
                        .setAction(action)
                        .setId(entity.getMessage().getMessageId())
                        .setEntity(entity.getClass().getSimpleName().toLowerCase()),
                entity.getUser());
    }
}
