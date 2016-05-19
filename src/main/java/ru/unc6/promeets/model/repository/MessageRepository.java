/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.Message;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    @Query(value = "select message from Message message where message.chat.chatId=(:chatId)")
    Iterable<Message> getMessagesByChatId(@Param("chatId") long chatId);

    @Query(value = "select message from Message message where message.chat.chatId=(:chatId) and message.time>=(:time)")
    Iterable<Message> getMessagesByChatIdAfter(@Param("chatId") long chatId, @Param("time") long time);
}
