package com.promeets.controller.rest;

import com.promeets.model.entity.MeetNote;
import com.promeets.model.service.entity.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Vladimir on 30.03.2016.
 */
@RestController
@RequestMapping(value = "/api/notes")
public class NoteController extends BaseRestController<MeetNote, Long> {

    @Autowired
    public NoteController(NoteService service) {
        super(service);
    }

}
