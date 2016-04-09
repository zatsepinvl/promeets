/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.controller.rest;

import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.service.entity.BaseService;

/**
 *
 * @author MDay
 */

@RestController
public class MessageController extends BaseRestController<Message>
{    
    public MessageController(BaseService<Message> service) {
        super(service);
    }   
}
