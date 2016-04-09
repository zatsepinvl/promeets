/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.entity;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.repository.MessageRepository;

/**
 *
 * @author MDay
 */
@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    MessageRepository messageRepository;
    
    @Override
    public Message getById(long id) {
        return messageRepository.findOne(id);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void delete(long id) {
        messageRepository.delete(id);
    }

    @Override
    public List<Message> getAll() {
        return (List)messageRepository.findAll();
    }
    
}
