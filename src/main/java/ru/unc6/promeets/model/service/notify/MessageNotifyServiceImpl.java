/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.notify;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.controller.AppSTOMPController;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.repository.ChatRepository;

/**
 *
 * @author MDay
 */
@Service
public class MessageNotifyServiceImpl implements MessageNotifyService
{
    @Autowired
    AppSTOMPController appSTOMPController;
    @Autowired
    ChatRepository chatRepository;

    @Async
    @Override
    public void onCreate(Message message) 
    {
        onAction(message, Notification.Action.CREATE);
    }

    @Async
    @Override
    public void onUpdate(Message message) 
    {
        onAction(message, Notification.Action.UPDATE);
    }

    @Async
    @Override
    public void onDelete(Message message) 
    {
        onAction(message, Notification.Action.DELETE);
    }
    
    public void onAction(Message message,Notification.Action action ) 
    {
        List<User> users = (List)chatRepository.getAllUsersByChatId(message.getChat().getChatId());
        Notification notification = new Notification(message.getClass(), action, message.getMessageId());
        for(User user : users){
            appSTOMPController.sendNotificationToUser(notification, user);
        } 
    }
    
}
