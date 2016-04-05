/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.controller.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.service.MessageService;

/**
 *
 * @author MDay
 */

@RestController
public class MessageController 
{
     @Autowired
    MessageService messageService;
    
    @Autowired 
    private SimpMessagingTemplate simpMessagingTemplate;
    
     @RequestMapping(value = "api/messages/{id}", method = RequestMethod.GET)
    public ResponseEntity<Message> getMessageById(@PathVariable long id) 
    {
        Message message = messageService.getById(id);
        
        if (message == null) 
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    
     @RequestMapping(value = "api/messages", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getMessages() 
    {
        List<Message> messages = messageService.getAll();
        
        if (messages.isEmpty()) 
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "api/messages/", method = RequestMethod.POST)
    public ResponseEntity<Void> createMessage(@RequestBody Message message) 
    { 
        if (message == null) 
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        messageService.save(message);
        
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "api/messages/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateMessage(@RequestBody Message message, @PathVariable long id) 
    { 
        if (messageService.getById(id) == null) 
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        message.setMessageId(id);
        messageService.save(message);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value = "api/messages/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> updateMessage(@PathVariable long id) 
    { 
        if (messageService.getById(id) == null) 
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        messageService.delete(id);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
