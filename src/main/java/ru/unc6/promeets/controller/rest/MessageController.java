/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.controller.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.service.entity.MessageService;
import ru.unc6.promeets.model.service.notify.MessageNotifyService;

/**
 *
 * @author MDay
 */

@RestController
@RequestMapping("/api/messages")
public class MessageController extends BaseRestController<Message>
{

    private static final Logger log = Logger.getLogger(MeetController.class);

    private MessageService messageService;
    private MessageNotifyService messageNotifyService;

    @Autowired
    public MessageController(MessageService service, MessageNotifyService notifyService) {
        super(service,notifyService);
        this.messageService = service;
        this.messageNotifyService = notifyService;
    }
}
