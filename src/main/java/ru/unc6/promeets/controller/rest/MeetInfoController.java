/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.controller.rest;

import java.io.Serializable;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.model.entity.UserMeetInfo;
import ru.unc6.promeets.model.service.entity.MeetInfoService;

/**
 *
 * @author Alex
 */
@RestController
@RequestMapping("/api/users/meets/info")
public class MeetInfoController{

    @Autowired
    MeetInfoService meetInfoService;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    private UserMeetInfo update(@RequestBody UserMeetInfo meetInfo, @PathVariable("id") Long meetId) {
        
        return meetInfoService.update(meetInfo);
    }
}
