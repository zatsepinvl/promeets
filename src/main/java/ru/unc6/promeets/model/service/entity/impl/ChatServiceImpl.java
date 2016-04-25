/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.entity.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.Chat;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.repository.MessageRepository;
import ru.unc6.promeets.model.repository.ChatRepository;
import ru.unc6.promeets.model.service.entity.ChatService;

@Service
@Transactional
public class ChatServiceImpl extends BaseServiceImpl<Chat, Long>
        implements ChatService {

    private ChatRepository chatRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository repository) {
        super(repository);
        this.chatRepository = repository;
    }


    @Override
    public void delete(Long id) {
        chatRepository.deleteAllMessagesByChatId(id);
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
