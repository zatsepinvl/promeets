/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.controller;

/**
 *
 * @author MDay
 */
 
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.unc6.promeets.models.entities.Meet;
import ru.unc6.promeets.service.MeetService;
 
@RestController
public class MainController 
{
    @Autowired
    MeetService meetService;
    
    @RequestMapping(value = "/addMeet", method = RequestMethod.POST)
    public ResponseEntity<Void> addMeet(@RequestBody Meet meet, UriComponentsBuilder ucBuilder) 
    {
        if (meetService.getById(meet.getMeetId())!= null) 
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
 
        meetService.addMeet(meet);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/addMeet").buildAndExpand(meet.getMeetId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/getMeet", method = RequestMethod.GET)
    public ResponseEntity<List<Meet>> getMeet() 
    {
        List<Meet> meets = new ArrayList<>();
        meets.add(meetService.getById(1));
        
        return new ResponseEntity<>(meets, HttpStatus.OK);
    }
    
 
}
