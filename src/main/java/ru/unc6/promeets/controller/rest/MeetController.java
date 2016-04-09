package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.controller.exception.NotFoundException;
import ru.unc6.promeets.controller.exception.ResponseError;
import ru.unc6.promeets.model.entity.*;
import ru.unc6.promeets.model.repository.MeetTypeRepository;
import ru.unc6.promeets.model.service.entity.BaseService;
import ru.unc6.promeets.model.service.entity.MeetService;

import java.util.List;

import org.apache.log4j.Logger;

@RestController
@RequestMapping("/api/meets")
public class MeetController extends BaseRestController<Meet> {

    private static final Logger log = Logger.getLogger(MeetController.class);

    private MeetService meetService;

    @Autowired
    public MeetController(MeetService service) {
        super(service);
        this.meetService = service;
    }


    @RequestMapping(value = "/{id}/user_meets", method = RequestMethod.GET)
    public List getUserMeets(@PathVariable("id") long id) {
        checkIsNotFound(id);
        return meetService.getUserMeets(id);
    }

    @RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
    public List getUsers(@PathVariable("id") long id) {
        checkIsNotFound(id);
        return meetService.getUsers(id);
    }

    @RequestMapping(value = "/{id}/notes", method = RequestMethod.GET)
    public List getMeetNotes(@PathVariable("id") long id) {
        checkIsNotFound(id);
        return meetService.getMeetNotes(id);
    }

    @RequestMapping(value = "/{id}/tasks", method = RequestMethod.GET)
    public List getMeetTargets(@PathVariable("id") long id) {
        checkIsNotFound(id);
        return meetService.getMeetAims(id);
    }

}