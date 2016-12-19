package com.promeets.controller.rest;

import com.promeets.controller.exception.ForbiddenRequestException;
import com.promeets.controller.exception.ForbiddenResponseErrorMessage;
import com.promeets.model.entity.Message;
import com.promeets.model.entity.User;
import com.promeets.model.entity.UserMessage;
import com.promeets.model.entity.UserMessagePK;
import com.promeets.model.service.entity.MessageService;
import com.promeets.model.service.entity.UserMessageService;
import com.promeets.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Vladimir on 13.04.2016.
 */
@RestController
@RequestMapping("/api/users/messages")
public class UserMessageController extends BaseUserRestController<UserMessage, UserMessagePK, Message> {

    private UserMessageService userMessageService;
    private MessageService messageService;

    @Autowired
    public UserMessageController(MessageService entityService, UserMessageService service) {
        super(entityService, service);
        this.messageService = entityService;
        this.userMessageService = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    public UserMessage create(@RequestBody UserMessage userMessage, @CurrentUser User user) {
        return userMessageService.create(userMessage);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserMessage get(@PathVariable("id") long messageId, @CurrentUser User user) {
        checkIsNotFound(messageId);
        UserMessage userMessage = userMessageService.getUserMessageByUserIdAndMessageId(user.getUserId(), messageId);
        checkHasOwnerAccess(userMessage, user);
        return userMessageService.getById(userMessage.getUserMessagePK());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public UserMessage update(@PathVariable("id") long messageId, @RequestBody UserMessage userMessage, @CurrentUser User user) {
        checkIsNotFound(messageId);
        checkHasOwnerAccess(userMessage, user);
        return userMessageService.update(userMessage);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long messageId, @CurrentUser User user) {
        checkIsNotFound(messageId);
        UserMessage userMessage = userMessageService.getUserMessageByUserIdAndMessageId(user.getUserId(), messageId);
        checkHasOwnerAccess(userMessage, user);
        userMessageService.delete(userMessage.getUserMessagePK());
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public List<UserMessage> getNeMessages(@CurrentUser User user) {
        return userMessageService.getNewUserMessagesByUserId(user.getUserId());
    }

    @Override
    protected void checkHasAccess(UserMessage entity, User user) {
        checkHasOwnerAccess(entity, user);
    }

    @Override
    protected void checkHasOwnerAccess(UserMessage entity, User user) {
        if (entity.getUser().getUserId() != user.getUserId()) {
            throw new ForbiddenRequestException().setResponseErrorMessage(new ForbiddenResponseErrorMessage());
        }
    }

}
