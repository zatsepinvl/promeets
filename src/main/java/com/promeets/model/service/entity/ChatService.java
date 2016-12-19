/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.service.entity;

import java.util.List;

import com.promeets.model.entity.Chat;
import com.promeets.model.entity.Message;
import org.springframework.data.domain.Pageable;


public interface ChatService extends BaseService<Chat,Long> {
    List<Message> getMessagePageByChatId(long id, Pageable page);
    List<Message> getAllMessagesByChatId(long id);
}


