/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.entity;

import ru.unc6.promeets.model.entity.Message;

import java.util.List;

public interface MessageService extends BaseService<Message, Long> {
    List<Message> getMessagesByChatId(long chatId);

    List<Message> getMessagesByChatIdAfter(long chatId, long time);
}
