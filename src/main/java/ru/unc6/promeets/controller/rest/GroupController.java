/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.controller.rest;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.model.entity.Chat;
import ru.unc6.promeets.model.entity.Group;
import ru.unc6.promeets.model.service.entity.GroupService;


@RestController
@RequestMapping("/api/groups")
public class GroupController extends BaseRestController<Group, Long> {
    private static final Logger log = Logger.getLogger(MeetController.class);


    private GroupService groupService;

    @Autowired
    public GroupController(GroupService service) {
        super(service);
        this.groupService = service;
    }


    @RequestMapping(value = "/{id}/meets", method = RequestMethod.GET)
    @ResponseBody
    public List getGroupMeetsByGroupIdAndMonth(@PathVariable long id, @RequestParam(value = "start", required = false) Long start, @RequestParam(value = "end", required = false) Long end) {
        checkIsNotFoundById(id);
        if (start == null || end == null) {
            return groupService.getMeetsByGroupId(id);
        }
        return groupService.getMeetsByGroupIdAndTimePeriod(id, start, end);
    }


    @RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
    public List getGroupUsersById(@PathVariable long id) {
        checkIsNotFoundById(id);
        return groupService.getUsersByGroupId(id);
    }

    @RequestMapping(value = "/{id}/user_groups", method = RequestMethod.GET)
    public List getGroupUserGroupsById(@PathVariable long id) {
        checkIsNotFoundById(id);
        return groupService.getUserGroupsByGroupId(id);
    }

    @RequestMapping(value = "/group_types", method = RequestMethod.GET)
    public ResponseEntity<List> getTypes() {
        return new ResponseEntity<List>(groupService.getGroupTypes(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/chat", method = RequestMethod.GET)
    public Chat getChatByGroupId(@PathVariable("id") long groupId) {
        return checkAndGetById(groupId).getChat();
    }

}
