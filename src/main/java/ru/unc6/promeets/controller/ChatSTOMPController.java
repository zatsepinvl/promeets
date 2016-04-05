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

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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


    private void sendAllMessagesToClient(Long id) 
    {
        Pageable page = new PageRequest(0,20);
        List messages = (List) chatService.getMessagePageByChatId(id, page);
        log.info(messages); 
        simpMessagingTemplate.convertAndSend("/topic/chat/"+id, messages);
    }
    
    
    @MessageMapping("/chat/{id}/get")
    public void getAllMessagesByClient(@DestinationVariable("id") Long id) 
    {
        sendAllMessagesToClient(id);
    }

    @MessageMapping("/chat/{id}/send")
    public void sendMessageByClient(Message message, @DestinationVariable("id") Long id) 
    {
        if (message.getText().trim().isEmpty())
        {
            log.info(message + " text is empty");
            return;
        }
        
        if (message.getChat()==null)
        {
            log.error(message + " chat is null");
            return;
        }
        
        message = chatService.addMessageByChatId(message, id);
         
        simpMessagingTemplate.convertAndSend("/topic/chat/"+id, message.getMessageId());
        
    }
   
   

}
