package com.promeets.model.service.notification.impl;

import com.promeets.model.entity.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promeets.controller.StompNotificationController;
import com.promeets.model.service.notification.Notification;

/**
 * Created by Vladimir on 19.04.2016.
 */
@Service
public class UserMessageNotificationService extends BaseNotificationServiceImpl<UserMessage> {
    @Autowired
    private StompNotificationController stompController;

    @Override
    protected void onAction(UserMessage entity, Notification.Action action) {
        stompController.notifyUser(new Notification()
                        .setData(entity)
                        .setAction(action)
                        .setId(entity.getMessage().getMessageId())
                        .setEntity(entity.getClass().getSimpleName().toLowerCase()),
                entity.getUser());
    }
}
