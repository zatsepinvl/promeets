/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.model.dao.MeetDAO;
import ru.unc6.promeets.model.repository.MeetRepository;

import java.util.List;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.service.MeetService;

@RestController
public class MeetController {

    @Autowired
    private MeetService meetService;


    
    @RequestMapping(value = "/meets", method = RequestMethod.GET)
    @ResponseBody
    public List getMeets() 
    {
        return meetService.getAll();
    }
    
    @RequestMapping(value = "/meets", method = RequestMethod.GET)
    public void createMeet(@RequestBody Meet meet) 
    {
        
    }
    
    
    @RequestMapping(value = "/meets/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Meet getMeetById(@PathVariable("id") long id) 
    {
        return meetService.getById(id);
    }

    @RequestMapping(value = "/meets/{id}/usermeets", method = RequestMethod.GET)
    @ResponseBody
    public List getMeetUsers(@PathVariable("id") long id) 
    {
        return meetService.getUserMeets(id);
    }
    
    @RequestMapping(value = "/meets/{id}/users", method = RequestMethod.GET)
    @ResponseBody
    public List getUsers(@PathVariable("id") long id) 
    {
        return (List) meetService.getUsers(id);
    }
    
    @RequestMapping(value = "/meets/{id}/notes", method = RequestMethod.GET)
    @ResponseBody
    public List getMeetNotes(@PathVariable("id") long id) 
    {
        return (List) meetService.getMeetNotes(id);
    }
    
    @RequestMapping(value = "/meets/{id}/targets", method = RequestMethod.GET)
    @ResponseBody
    public List getMeetTargets(@PathVariable("id") long id) 
    {
        return (List) meetService.getMeetTargets(id);
    }


}
