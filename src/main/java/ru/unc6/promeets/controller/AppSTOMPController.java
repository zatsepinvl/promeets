/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.service.notify.Notification;

/**
 *
 * @author MDay
 */

@Controller
public class AppSTOMPController 
{
    @Autowired 
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/{id}/init")
    public void initUser(@DestinationVariable("id") Long id) 
    {
        simpMessagingTemplate.convertAndSend("/topic/"+id, "{\"status\":\"ready\"}");
    }
    
    public void sendNotificationToUser (Notification notification, User user)
    {
        simpMessagingTemplate.convertAndSend("/topic/"+user.getUserId(), notification);
    }
}
