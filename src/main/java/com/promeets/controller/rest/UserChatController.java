package com.promeets.controller.rest;

import com.promeets.model.entity.*;
import com.promeets.model.service.entity.UserChatService;
import com.promeets.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.promeets.model.service.entity.ChatService;
import com.promeets.model.service.entity.UserMessageService;

import java.util.List;

/**
 * Created by Vladimir on 13.04.2016.
 */
@RestController
@RequestMapping("/api/users/chats")
public class UserChatController extends BaseUserRestController<UserChat, UserChatPK, Chat> {

    private ChatService chatService;
    private UserChatService userChatService;

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    public UserChatController(ChatService entityService, UserChatService service) {
        super(entityService, service);
        this.chatService = entityService;
        this.userChatService = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserChat> getAllByUser(@CurrentUser User user) {
        return userChatService.getUserChatsByUserId(user.getUserId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserChat getById(@PathVariable("id") long chatId, @CurrentUser User user) {
        return userChatService.getUserChatByUserIdAndChatId(user.getUserId(), chatId);
    }

    @RequestMapping(value = "/{id}/messages", method = RequestMethod.GET)
    public List<UserMessage> getMessagesByChatId(@PathVariable("id") long chatId, @RequestParam(value = "page") int page, @CurrentUser User user) {
        checkIsNotFound(chatId);
        return userMessageService.getUserMessagesByUserIdAndChatId(user.getUserId(), chatId, page);
    }


    @Override
    protected void checkHasAccess(UserChat entity, User user) {

    }

    @Override
    protected void checkHasOwnerAccess(UserChat entity, User user) {

    }
}
