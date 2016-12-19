/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.controller.rest;

import com.promeets.model.entity.UserMeetInfo;
import com.promeets.model.service.entity.MeetInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
