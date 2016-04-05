package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.model.entity.*;
import ru.unc6.promeets.model.repository.MeetTypeRepository;
import ru.unc6.promeets.model.service.entity.MeetService;

import java.util.List;

import org.apache.log4j.Logger;

@RestController
public class MeetController {

    private static final Logger log = Logger.getLogger(MeetController.class);

    @Autowired
    private MeetService meetService;

    @Autowired
    private MeetTypeRepository meetTypesRepository;

    @RequestMapping(value = "api/meets", method = RequestMethod.GET)
    public ResponseEntity<List<Meet>> getMeets() {
        List<Meet> meets = meetService.getAll();
        if (meets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(meets, HttpStatus.OK);
    }

    @RequestMapping(value = "api/meets/{id}", method = RequestMethod.GET)
    public ResponseEntity<Meet> getMeetById(@PathVariable("id") long id) {
        Meet meet = meetService.getById(id);
        if (meet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(meet, HttpStatus.OK);
    }

    @RequestMapping(value = "api/meets", method = RequestMethod.POST)
    public ResponseEntity<Void> createMeet(@RequestBody Meet meet) {
        if (meet == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        meetService.save(meet);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "api/meets/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateMeet(@RequestBody Meet meet, @PathVariable long id) {
        if (meetService.getById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        meet.setMeetId(id);
        meetService.save(meet);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "api/meets/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteMeet(@PathVariable long id) {
        if (meetService.getById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        meetService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "api/meets/{id}/user_meets", method = RequestMethod.GET)
    public ResponseEntity<List<UserMeet>> getUserMeets(@PathVariable("id") long id) {
        List<UserMeet> userMeets = meetService.getUserMeets(id);
        if (userMeets == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userMeets, HttpStatus.OK);
    }

    @RequestMapping(value = "api/meets/{id}/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers(@PathVariable("id") long id) {
        List<User> users = meetService.getUsers(id);
        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "api/meets/{id}/notes", method = RequestMethod.GET)
    public ResponseEntity<List<MeetNote>> getMeetNotes(@PathVariable("id") long id) {
        List<MeetNote> notes = meetService.getMeetNotes(id);
        if (notes == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @RequestMapping(value = "api/meets/{id}/aims", method = RequestMethod.GET)
    public ResponseEntity<List<MeetAim>> getMeetTargets(@PathVariable("id") long id) {
        List<MeetAim> aims = meetService.getMeetAims(id);
        if (aims == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(aims, HttpStatus.OK);
    }

    @RequestMapping(value = "api/meet_types", method = RequestMethod.GET)
    public ResponseEntity<List> getTypes() {
        List types = (List) meetTypesRepository.findAll();
        return new ResponseEntity<>(types, types.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }


}