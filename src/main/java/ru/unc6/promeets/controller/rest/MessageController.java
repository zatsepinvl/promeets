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
import ru.unc6.promeets.model.service.notification.MessageNotificationService;

/**
 *
 * @author MDay
 */

@RestController
@RequestMapping("/api/messages")
public class MessageController extends BaseRestController<Message,Long>
{

    private static final Logger log = Logger.getLogger(MeetController.class);

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService service) {
        super(service);
        this.messageService = service;
    }
}
