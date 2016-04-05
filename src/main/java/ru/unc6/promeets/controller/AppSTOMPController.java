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

/**
 *
 * @author MDay
 */

@Controller
public class AppSTOMPController 
{
    @Autowired 
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @MessageMapping("/chat/{id}/init")
    public void initUser(@DestinationVariable("id") Long id) 
    {
        simpMessagingTemplate.convertAndSend("/topic/chat/"+id, "{\"status\":\"ready\"}");
    }
   
}
