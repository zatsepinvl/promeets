/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.controller.rest;

import java.util.List;

import com.promeets.model.entity.Chat;
import com.promeets.model.service.entity.ChatService;
import com.promeets.model.service.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chats")
public class ChatController extends BaseRestController<Chat, Long> {
    private ChatService chatService;

    @Autowired
    private UserService userService;
    @Autowired
    public ChatController(ChatService service) {
        super(service);
        this.chatService = service;
    }

    @RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
    public List getChatUsers(@PathVariable("id") long chatId) {
        return userService.getUsersByChatId(chatId);
    }
}
