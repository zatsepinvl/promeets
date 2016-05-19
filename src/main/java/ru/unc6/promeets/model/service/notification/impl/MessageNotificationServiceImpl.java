/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.controller.NotificationController;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.service.entity.UserService;
import ru.unc6.promeets.model.service.notification.MessageNotificationService;
import ru.unc6.promeets.model.service.notification.Notification;

@Service
public class MessageNotificationServiceImpl extends BaseNotificationServiceImpl<Message> implements MessageNotificationService {

    @Autowired
    private NotificationController notificationController;

    @Autowired
    private UserService userService;

    @Override
    protected void onAction(Message message, Notification.Action action) {
        Notification notification = new Notification()
                .setData(message)
                .setEntity(message.getClass().getSimpleName().toLowerCase())
                .setAction(action)
                .setId(message.getMessageId());
        for (User user : userService.getUsersByChatId(message.getChat().getChatId())) {
            if (user.getUserId() != message.getUser().getUserId() || action == Notification.Action.UPDATE) {
                notificationController.sendNotificationToUser(notification, user);
            }
        }
    }

}
