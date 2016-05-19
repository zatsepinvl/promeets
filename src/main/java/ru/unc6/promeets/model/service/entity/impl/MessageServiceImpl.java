/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.repository.MessageRepository;
import ru.unc6.promeets.model.service.entity.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, Long>
        implements MessageService {

    private MessageRepository messageRepository;

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
}
