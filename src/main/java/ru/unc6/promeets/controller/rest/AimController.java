package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.MeetAim;
import ru.unc6.promeets.model.repository.AimRepository;

import java.util.List;

/**
 * Created by Vladimir on 11.02.2016.
 */
@RestController
public class AimController {

    @Autowired
    private AimRepository aimRepository;

    @RequestMapping(value = "api/meet_aims", method = RequestMethod.GET)
    public ResponseEntity<List> getAims() {
        List aims = (List) aimRepository.findAll();
        return new ResponseEntity<>(aims, aims.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @RequestMapping(value = "api/meet_aims/{id}", method = RequestMethod.GET)
    public ResponseEntity<MeetAim> getMeetById(@PathVariable("id") long id) {
        MeetAim meetAim = aimRepository.findOne(id);
        return new ResponseEntity<>(meetAim, meetAim == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @RequestMapping(value = "api/meet_aims", method = RequestMethod.POST)
    public ResponseEntity<MeetAim> createMeet(@RequestBody MeetAim aim) {
        return new ResponseEntity<>(aimRepository.save(aim), HttpStatus.OK);
    }

    @RequestMapping(value = "api/meet_aims/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateMeet(@RequestBody MeetAim aim, @PathVariable long id) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        if (aimRepository.findOne(id) != null) {
            aimRepository.save(aim);
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(httpStatus);
    }

    @RequestMapping(value = "api/meet_aims/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteMeet(@PathVariable long id) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (aimRepository.findOne(id) != null) {
            aimRepository.delete(id);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }

}
