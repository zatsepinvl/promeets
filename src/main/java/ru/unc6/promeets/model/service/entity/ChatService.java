/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.entity;

import java.util.List;
import org.springframework.data.domain.Pageable;

import ru.unc6.promeets.model.entity.Chat;
import ru.unc6.promeets.model.entity.Message;


public interface ChatService extends BaseService<Chat,Long> {
    List<Message> getMessagePageByChatId(long id, Pageable page);
    List<Message> getAllMessagesByChatId(long id);
    List getAllUsersByChatId(long id);
}


