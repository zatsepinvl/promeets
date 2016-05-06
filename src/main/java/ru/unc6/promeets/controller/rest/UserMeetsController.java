package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserMeet;
import ru.unc6.promeets.model.service.entity.MeetService;
import ru.unc6.promeets.model.service.entity.UserMeetService;
import ru.unc6.promeets.security.CurrentUser;

import java.util.List;

/**
 * Created by Vladimir on 10.04.2016.
 */
@RestController
@RequestMapping("/api/users/meets")
public class UserMeetsController {

    @Autowired
    private UserMeetService userMeetService;

    @Autowired
    private MeetService meetService;

    @RequestMapping(method = RequestMethod.GET)
    public List getAll(@CurrentUser User user) {
        return userMeetService.getUserMeetsByUserId(user.getUserId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserMeet getById(@PathVariable("id") long meetId, @CurrentUser User user) {
        return userMeetService.getUserMeetByUserIdAndMeetId(user.getUserId(), meetId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    private UserMeet update(@RequestBody UserMeet userMeet, @PathVariable("id") Long meetId) {
        
        return userMeetService.update(userMeet);
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    private List getNewMeet(@CurrentUser User user) {
        return userMeetService.getNotViewedMeetsByUserId(user.getUserId());
    }

}
