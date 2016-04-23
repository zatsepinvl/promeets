package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.model.entity.MeetNote;
import ru.unc6.promeets.model.service.entity.BaseService;
import ru.unc6.promeets.model.service.entity.NoteService;

import java.util.HashMap;

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
