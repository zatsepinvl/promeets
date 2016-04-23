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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.model.entity.Chat;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.service.entity.ChatService;

/**
 * @author MDay
 */

@RestController
@RequestMapping("/api/chats")
public class ChatController extends BaseRestController<Chat, Long> {
    private ChatService chatService;

    @Autowired
    public ChatController(ChatService service) {
        super(service);
        this.chatService = service;
    }

    @RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
    public List getChatUsers(@PathVariable long id) {
        return chatService.getAllUsersByChatId(id);
    }
}
