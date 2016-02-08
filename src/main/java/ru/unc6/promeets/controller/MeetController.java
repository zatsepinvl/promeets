/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.model.dao.MeetDAO;
import ru.unc6.promeets.model.repository.MeetRepository;

import java.util.List;

@RestController
public class MeetController {


    private MeetDAO meetDAO;
    private MeetRepository meetRepository;

    @Autowired
    public void setMeetRepository(MeetRepository meetRepository) {
        this.meetRepository = meetRepository;
    }

    @Autowired
    public void setMeetDAO(MeetDAO meetDAO) {
        this.meetDAO = meetDAO;
    }


    @RequestMapping(value = "/meet", method = RequestMethod.GET)
    @ResponseBody
    public List getMeets() {
        return (List) meetRepository.findAll();
    }

    @RequestMapping(value = "/meet/{id}/usermeet", method = RequestMethod.GET)
    @ResponseBody
    public List getMeetUsers(@PathVariable("id") long id) {
        return (List) meetRepository.getAllUserMeetsByMeetId(id);
    }

    @RequestMapping(value = "/meet/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteMeet(@PathVariable("id") long id) {
        meetRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
