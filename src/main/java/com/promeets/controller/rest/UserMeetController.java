package com.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.promeets.model.entity.User;
import com.promeets.model.entity.UserMeet;
import com.promeets.model.service.entity.MeetService;
import com.promeets.model.service.entity.UserMeetService;
import com.promeets.security.CurrentUser;

import java.util.List;

/**
 * Created by Vladimir on 10.04.2016.
 */
@RestController
@RequestMapping("/api/users/meets")
public class UserMeetController {

    @Autowired
    private UserMeetService userMeetService;

    @Autowired
    private MeetService meetService;


    @RequestMapping(method = RequestMethod.POST)
    public UserMeet create(@CurrentUser User user, @RequestBody UserMeet userMeet) {
        return userMeetService.create(userMeet);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserMeet getById(@PathVariable("id") long meetId, @CurrentUser User user) {
        return userMeetService.getUserMeetByUserIdAndMeetId(user.getUserId(), meetId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable("id") long meetId, @CurrentUser User user) {
        userMeetService.deleteUserMeetByUserIdAndMeetId(user.getUserId(), meetId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserMeet> getAllByUserIdAndTimePeriod(@RequestParam("start") long start, @RequestParam("end") long end, @CurrentUser User user) {
        return userMeetService.getUserMeetsByUserIdAndTime(user.getUserId(), start, end);
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
