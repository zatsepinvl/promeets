/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.Message;

/**
 *
 * @author MDay
 */
public interface ChatMessageRepository extends CrudRepository<Message, Long>
{
    @Query("select message from Message message where message.chat.chatId=(:chatId)")
    Iterable<Message> getAllMessagesByChatId(@Param("chatId") Long id);
}
