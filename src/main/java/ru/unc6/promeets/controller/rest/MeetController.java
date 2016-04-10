package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.model.entity.*;
import ru.unc6.promeets.model.service.entity.MeetService;

import java.util.List;

import org.apache.log4j.Logger;
import ru.unc6.promeets.model.service.entity.UserMeetService;

@RestController
@RequestMapping("/api/meets")
public class MeetController extends BaseRestController<Meet,Long> {

    private static final Logger log = Logger.getLogger(MeetController.class);

    private MeetService meetService;

    private UserMeetService userMeetService;

    @Autowired
    public MeetController(MeetService service) {
        super(service);
        this.meetService = service;
    }


    @RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
    public List getUsers(@PathVariable("id") long id) {
        checkIsNotFoundById(id);
        return userMeetService.getUserMeetsByMeetId(id);
    }

    @RequestMapping(value = "/{id}/notes", method = RequestMethod.GET)
    public List getMeetNotes(@PathVariable("id") long id) {
        checkIsNotFoundById(id);
        return meetService.getMeetNotes(id);
    }

    @RequestMapping(value = "/{id}/tasks", method = RequestMethod.GET)
    public List getMeetTargets(@PathVariable("id") long id) {
        checkIsNotFoundById(id);
        return meetService.getMeetAims(id);
    }

}