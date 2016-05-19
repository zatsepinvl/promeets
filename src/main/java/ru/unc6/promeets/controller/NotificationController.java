/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.service.notification.Notification;
import ru.unc6.promeets.model.service.webrtc.WebRtcSignalMessage;
import ru.unc6.promeets.model.service.webrtc.WebRtcSignalService;

/**
 * @author MDay
 */

@Controller
public class NotificationController
{
    @Autowired 
    private SimpMessagingTemplate simpMessagingTemplate;
    
    
    @Autowired
    private WebRtcSignalService rtcSignalService;

    @MessageMapping("/{id}/init")
    public void initUser(@DestinationVariable("id") Long id) 
    {
        simpMessagingTemplate.convertAndSend("/topic/"+id, "{\"status\":\"ready\"}");
    }
    
    @MessageMapping("/rtc/{id}")
    public void rtc(WebRtcSignalMessage message, Principal principal) 
    {
        rtcSignalService.signalRTCByMeetId(message, principal);
    }
    
    public void sendNotificationToUser (Notification notification, User user)
    {
        simpMessagingTemplate.convertAndSend("/topic/"+user.getUserId(), notification);
    }
    
    public void sendRtcSignalMessage (WebRtcSignalMessage message)
    {
        simpMessagingTemplate.convertAndSend("/topic/"+message.getDuserId(), message);
    }
}
