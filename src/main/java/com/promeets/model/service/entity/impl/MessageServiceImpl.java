/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.service.entity.impl;

import com.promeets.model.entity.Message;
import com.promeets.model.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promeets.model.service.entity.MessageService;
import com.promeets.model.service.entity.UserMessageService;

import java.util.List;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, Long>
        implements MessageService {

    private MessageRepository messageRepository;

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    public MessageServiceImpl(MessageRepository repository) {
        super(repository);
        this.messageRepository = repository;
    }

    @Override
    public List<Message> getMessagesByChatId(long chatId) {
        return (List<Message>) messageRepository.getMessagesByChatId(chatId);
    }

    @Override
    public List<Message> getMessagesByChatIdAfter(long chatId, long time) {
        return (List<Message>) messageRepository.getMessagesByChatIdAfter(chatId, time);
    }

    @Override
    public void deleteMessagesByChatId(long chatId) {
        userMessageService.deleteUserMessagesByChatId(chatId);
        messageRepository.deleteMessagesByChatId(chatId);
    }
}
