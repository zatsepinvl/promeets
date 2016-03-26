/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.postgresql.util.PGTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.Chat;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.repository.ChatMessageRepository;
import ru.unc6.promeets.model.repository.ChatRepository;

/**
 * @author MDay
 */

@Service
@Transactional
public class ChatServiceImpl implements ChatService {
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    ChatMessageRepository messageRepository;

    @Override
    public Chat getById(long id) {
        return chatRepository.findOne(id);
    }

    @Override
    public void save(Chat chat) {
        chatRepository.save(chat);
    }

    @Override
    public void delete(long id) {
        chatRepository.deleteAllMessagesByChatId(id);
        chatRepository.delete(id);
    }

    @Override
    public List<Chat> getAll() {
        return (List<Chat>) chatRepository.findAll();
    }

    @Override
    public List<Message> getAllMessagesByChatId(long id) {
        return (List<Message>) chatRepository.getAllMessagesByChatId(id);
    }

    @Override
    @Transactional
    public Message addMessageByChatId(Message message, long id) {
        Chat chat = getById(id);
        message.setChat(chat);
        message.setTime(new Timestamp(new Date().getTime()));
        
        return messageRepository.save(message);
    }

}
