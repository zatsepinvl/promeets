package ru.unc6.promeets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @ResponseBody
    public List getAims() {
        return (List) aimRepository.findAll();
    }

    @RequestMapping(value = "api/meet_aims/{id}", method = RequestMethod.GET)
    @ResponseBody
    public MeetAim getMeetById(@PathVariable("id") long id) {
        return aimRepository.findOne(id);
    }

    @RequestMapping(value = "api/meet_aims", method = RequestMethod.POST)
    @ResponseBody
    public MeetAim createMeet(@RequestBody MeetAim aim) {
        return aimRepository.save(aim);
    }

    @RequestMapping(value = "api/meet_aims/{id}", method = RequestMethod.PUT)
    public void updateMeet(@RequestBody MeetAim aim, @PathVariable long id) {
        if (aimRepository.findOne(id) != null) {
            aimRepository.save(aim);
        }

    }

    @RequestMapping(value = "api/meet_aims/{id}", method = RequestMethod.DELETE)
    public void deleteMeet(@PathVariable long id) {
        aimRepository.delete(id);
    }

}
