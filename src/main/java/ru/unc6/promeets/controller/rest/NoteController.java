package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.model.entity.MeetNote;
import ru.unc6.promeets.model.service.entity.NoteService;

import java.util.HashMap;

/**
 * Created by Vladimir on 30.03.2016.
 */
@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;


    @RequestMapping(value = "/api/notes", method = RequestMethod.POST)
    public MeetNote create(@RequestBody MeetNote note) {
        return noteService.save(note);
    }

    @RequestMapping(value = "/api/notes/{noteId}", method = RequestMethod.PUT)
    public ResponseEntity<MeetNote> update(@PathVariable("noteId") long id) {
        MeetNote meetNote = noteService.getById(id);
        if (meetNote == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(noteService.save(meetNote), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/notes/{noteId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("noteId") long id) {
        MeetNote meetNote = noteService.getById(id);
        if (meetNote == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        noteService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
