/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.service.ChatService;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 * @author MDay
 */
@Controller
public class ChatSTOMPController 
{
    private static final Logger log = Logger.getLogger(ChatSTOMPController.class);
    
    @Autowired 
    private SimpMessagingTemplate simpMessagingTemplate;  
    @Autowired
    private TaskScheduler scheduler;
    
    @Autowired
    private ChatService chatService;


    private void getChat(Long id) 
    {
        List messages = (List) chatService.getAllMessagesByChatId(id);
        log.info(messages); 
        simpMessagingTemplate.convertAndSend("/topic/chat", messages);
    }

    @MessageMapping("/chat/{id}/send")
    public void sendMessage(Message message, @DestinationVariable("id") Long id) 
    {
        chatService.addMessageByChatId(message, id);
       // getChat(1L);
    }
   
    @PostConstruct
    private void broadcastTimePeriodically() 
    {
      scheduler.scheduleAtFixedRate(new Runnable()
      {
        @Override public void run() 
        {
          //getChat(1L);
        }
      }, 5000);
    }

}
