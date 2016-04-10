/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.controller.rest;

import java.util.List;
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
import ru.unc6.promeets.model.service.entity.BaseService;
import ru.unc6.promeets.model.service.entity.ChatService;
import ru.unc6.promeets.model.service.notify.ChatNotifyService;
import ru.unc6.promeets.model.service.notify.MessageNotifyService;

/**
 *
 * @author MDay
 */

@RestController
@RequestMapping("/api/chats")
public class ChatController extends BaseRestController<Chat>
{
    private ChatService chatService;
    private ChatNotifyService chatNotifyService;
    
    @Autowired
    private MessageNotifyService messageNotifyService;

    @Autowired
    public ChatController(ChatService service, ChatNotifyService notifyService) {
        super(service,notifyService);
        this.chatService = service;
        this.chatNotifyService = notifyService;
    }
    
    @RequestMapping(value = "/{id}/messages/{pageId}", method = RequestMethod.GET)
    public List<Message> getChatMessages(@PathVariable long id, @PathVariable long pageId) 
    { 
        Sort sort = new Sort(Sort.Direction.DESC, "time");
        Pageable page = new PageRequest((int) pageId, 20, sort);
        
        List<Message> messages = chatService.getMessagePageByChatId(id, page);
        
        return messages;
    }
    
    @RequestMapping(value = "/{id}/messages", method = RequestMethod.POST)
    public Message addChatMessage(@PathVariable long id, @RequestBody Message message) 
    { 
        message = chatService.addMessageByChatId(message, id);
        messageNotifyService.onCreate(message);
        return message;
    }
    
    @RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
    public List<User> getChatUsers(@PathVariable long id) 
    { 
        List<User> users = chatService.getAllUsersByChatId(id);
        return users;
    }
}
