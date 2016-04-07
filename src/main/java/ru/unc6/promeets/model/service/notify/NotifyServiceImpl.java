/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.notify;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.unc6.promeets.model.entity.User;

/**
 *
 * @author MDay
 */
@Component
public class NotifyServiceImpl implements NotifyService
{
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @Override
    public void sendNotifyMessage(User user, Class entityClass, Long entityId, Notify.Action action) 
    {
        Notify notify = new Notify(entityClass, action, entityId);
        String destination = "/topic/"+user.getUserId();
        simpMessagingTemplate.convertAndSend(destination, notify);
    }

    @Override
    public void sendNotifyMessage(List<User> users, Class entityClass, Long entityId, Notify.Action action) 
    {
        for (User user:users)
        {
            sendNotifyMessage(user, entityClass, entityId, action);
        }
    }
    
}
