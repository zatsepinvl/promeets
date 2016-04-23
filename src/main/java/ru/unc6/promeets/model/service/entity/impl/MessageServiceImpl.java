/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.repository.MessageRepository;
import ru.unc6.promeets.model.service.entity.MessageService;
import ru.unc6.promeets.model.service.entity.UserMessageService;
import ru.unc6.promeets.model.service.notification.MessageNotificationService;

/**
 * @author MDay
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, Long>
        implements MessageService {


    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private MessageNotificationService messageNotificationService;


    @Autowired
    public MessageServiceImpl(MessageRepository repository) {
        super(repository);
    }


    @Override
    public Message create(Message entity) {
        Message message = super.create(entity);
        userMessageService.createUserMessagesByMessage(entity);
        messageNotificationService.onCreate(entity);
        return message;
    }
}
