
package com.promeets.controller.rest;

import com.promeets.model.entity.Message;
import com.promeets.model.service.entity.ChatService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.List;

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
       // chatService.addMessageByChatId(message, id);
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
