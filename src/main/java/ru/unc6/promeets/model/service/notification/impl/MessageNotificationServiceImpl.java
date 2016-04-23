/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.controller.AppSTOMPController;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.repository.ChatRepository;
import ru.unc6.promeets.model.service.notification.MessageNotificationService;
import ru.unc6.promeets.model.service.notification.Notification;

@Service
public class MessageNotificationServiceImpl extends BaseNotificationServiceImpl<Message> implements MessageNotificationService {
    @Autowired
    private AppSTOMPController appSTOMPController;
    @Autowired
    private ChatRepository chatRepository;

    @Override
    protected void onAction(Message message, Notification.Action action) {
        Notification notification = new Notification(message.getClass(), action, message.getMessageId());
        for (User user : chatRepository.getAllUsersByChatId(message.getChat().getChatId())) {
            if (user.getUserId() != message.getUser().getUserId()) {
                appSTOMPController.sendNotificationToUser(notification, user);
            }
        }
    }

}
