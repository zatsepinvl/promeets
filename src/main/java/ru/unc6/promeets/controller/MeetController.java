/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.MeetNote;
import ru.unc6.promeets.model.entity.MeetTarget;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserMeet;
import ru.unc6.promeets.model.service.MeetService;

@RestController
public class MeetController {

    @Autowired
    private MeetService meetService;


    
    @RequestMapping(value = "/meets", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Meet>> getMeets() 
    {
        List<Meet> meets = meetService.getAll();
        if (meets.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(meets, HttpStatus.OK);
    }
  
    @RequestMapping(value = "/meets/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Meet> getMeetById(@PathVariable("id") long id) 
    {
        Meet meet = meetService.getById(id);
        if (meet == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(meet, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/meets", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Void> createMeet(@RequestBody Meet meet) 
    {
        if (meet == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        meetService.save(meet);
        
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/meets/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateMeet(@RequestBody Meet meet, @PathVariable long id) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @RequestMapping(value = "/meets/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteMeet(@PathVariable long id) 
    {
        if (meetService.getById(id) == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        meetService.delete(id);
   
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/meets/{id}/usermeets", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<UserMeet>> getUserMeets(@PathVariable("id") long id) 
    {
        List<UserMeet> userMeets = meetService.getUserMeets(id);
        
        if (userMeets == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(userMeets, HttpStatus.OK); 
    }
    
    @RequestMapping(value = "/meets/{id}/users", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> getUsers(@PathVariable("id") long id) 
    {
        List<User> users = meetService.getUsers(id);
        
        if (users == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(users, HttpStatus.OK); 
    }
    
    @RequestMapping(value = "/meets/{id}/notes", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<MeetNote>> getMeetNotes(@PathVariable("id") long id) 
    {
        List<MeetNote> notes = meetService.getMeetNotes(id);
        
        if (notes == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/meets/{id}/targets", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<MeetTarget>> getMeetTargets(@PathVariable("id") long id) 
    {
        List<MeetTarget> targets = meetService.getMeetTargets(id);
        
        if (targets == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(targets, HttpStatus.OK);
    }


}
