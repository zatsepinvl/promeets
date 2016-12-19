/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.repository;

import com.promeets.model.entity.Message;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    @Query(value = "select message from Message message where message.chat.chatId=(:chatId)")
    Iterable<Message> getMessagesByChatId(@Param("chatId") long chatId);

    @Query(value = "select message from Message message where message.chat.chatId=(:chatId) and message.time>=(:time)")
    Iterable<Message> getMessagesByChatIdAfter(@Param("chatId") long chatId, @Param("time") long time);

    @Modifying
    @Query("delete from Message message where message.chat.chatId=(:chatId)")
    void deleteMessagesByChatId(@Param("chatId") Long id);
}
