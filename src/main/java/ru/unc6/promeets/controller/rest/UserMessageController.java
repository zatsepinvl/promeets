package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.controller.exception.ForbiddenRequestException;
import ru.unc6.promeets.controller.exception.ForbiddenResponseError;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserMessage;
import ru.unc6.promeets.model.entity.UserMessagePK;
import ru.unc6.promeets.model.service.entity.MessageService;
import ru.unc6.promeets.model.service.entity.UserMessageService;
import ru.unc6.promeets.security.CurrentUser;

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
        return userMessageService.save(userMessage);
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
            throw new ForbiddenRequestException().setResponseError(new ForbiddenResponseError());
        }
    }

}
