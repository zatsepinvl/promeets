/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
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
public class ChatServiceImpl extends BaseServiceImpl<Chat, Long>
        implements ChatService {

    private ChatRepository chatRepository;

    @Autowired
    private ChatMessageRepository messageRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository repository) {
        super(repository);
        this.chatRepository = repository;
    }

    @Override
    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public void delete(Long id) {
        chatRepository.deleteMessagesByChatId(id);
        chatRepository.delete(id);
    }

    @Override
    public List<Message> getMessagesByChatId(long id) {
        return (List<Message>) chatRepository.getMessagesByChatId(id);
    }
}
