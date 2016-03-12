/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service;

import java.util.List;
import ru.unc6.promeets.model.entity.Chat;
import ru.unc6.promeets.model.entity.Message;

/**
 *
 * @author MDay
 */
public interface ChatService extends BaseService<Chat>
{
    public void addMessageByChatId(Message message, long id);
    public List<Message> getAllMessagesByChatId(long id);
}
