/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.controller.rest;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.model.entity.Chat;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.service.entity.ChatService;
import ru.unc6.promeets.model.service.notify.Notify;

/**
 *
 * @author MDay
 */

@RestController
public class ChatController extends BaseRestController<Chat>
{
   private static final Logger log = Logger.getLogger(MeetController.class);

    private ChatService chatService;

    @Autowired
    public ChatController(ChatService service) 
    {
        super(service);
        this.chatService = service;
    }
    
    @RequestMapping(value = "api/chats/{id}/messages", method = RequestMethod.GET)
    public List getChatAllMessages(@PathVariable long id) 
    { 
        List<Message> messages = chatService.getAllMessagesByChatId(id);
        checkIsNotFound(id);
        return messages;
    }
    
    @RequestMapping(value = "api/chats/{id}/messages/{pageId}", method = RequestMethod.GET)
    public List<Message> getChatMessages(@PathVariable long id, @PathVariable long pageId) 
    { 
        Sort sort = new Sort(Sort.Direction.DESC, "time");
        Pageable page = new PageRequest((int) pageId, 20, sort);
        
        List<Message> messages = chatService.getMessagePageByChatId(id, page);
        
        checkIsNotFound(id);
        return messages;
    }
    
    @RequestMapping(value = "api/chats/{id}/messages", method = RequestMethod.POST)
    public Message addChatMessage(@PathVariable long id, @RequestBody Message message) 
    { 
        checkIsNotFound(id);
        message = chatService.addMessageByChatId(message, id);
         
        return message;
    }
    
    @RequestMapping(value = "api/chats/{id}/users", method = RequestMethod.GET)
    public List<User> getChatUsers(@PathVariable long id) 
    { 
        checkIsNotFound(id);
        List<User> users = chatService.getAllUsersByChatId(id);
        return users;
    }
}
