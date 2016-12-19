/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.controller.rest;

import com.promeets.model.entity.Message;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.promeets.model.service.entity.MessageService;


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
