/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.Chat;
import ru.unc6.promeets.model.entity.Message;

/**
 *
 * @author MDay
 */
public interface ChatRepository extends CrudRepository<Chat, Long>
{
    @Modifying
    @Query("select message from Message message where message.chat.chatId=(:chatId) order by message.time")
    Iterable<Message> getMessagesByChatId(@Param("chatId") Long id);
    
    @Modifying
    @Query("delete from Message message where message.chat.chatId=(:chatId)")
    void deleteMessagesByChatId(@Param("chatId") Long id);
}
