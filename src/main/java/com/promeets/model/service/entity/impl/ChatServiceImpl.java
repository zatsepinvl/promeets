/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.service.entity.impl;

import java.util.List;

import com.promeets.model.entity.Message;
import com.promeets.model.service.entity.UserChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promeets.model.entity.Chat;
import com.promeets.model.repository.ChatRepository;
import com.promeets.model.service.entity.ChatService;
import com.promeets.model.service.entity.MessageService;
import com.promeets.model.service.entity.UserService;

@Service
@Transactional
public class ChatServiceImpl extends BaseServiceImpl<Chat, Long>
        implements ChatService {

    private ChatRepository chatRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserChatService userChatService;

    @Autowired
    private MessageService messageService;

    @Autowired
    public ChatServiceImpl(ChatRepository repository) {
        super(repository);
        this.chatRepository = repository;
    }

    @Override
    public void delete(Long id) {
        messageService.deleteMessagesByChatId(id);
        chatRepository.delete(id);
    }

    @Override
    public List<Message> getMessagePageByChatId(long id, Pageable page) {
        return chatRepository.getMessagesPageByChatId(id, page).getContent();
    }

    @Override
    public List<Message> getAllMessagesByChatId(long id) {
        return (List<Message>) chatRepository.getAllMessagesByChatId(id);
    }
}
