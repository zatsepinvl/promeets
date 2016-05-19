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
import ru.unc6.promeets.controller.exception.BadRequestException;
import ru.unc6.promeets.model.entity.Chat;
import ru.unc6.promeets.model.entity.Group;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.service.entity.GroupService;
import ru.unc6.promeets.model.service.entity.UserGroupInviteService;
import ru.unc6.promeets.model.service.entity.UserGroupService;
import ru.unc6.promeets.model.service.entity.UserMeetService;
import ru.unc6.promeets.security.CurrentUser;


@RestController
@RequestMapping("/api/groups")
public class GroupController extends BaseRestController<Group, Long> {
    private static final Logger log = Logger.getLogger(MeetController.class);


    private GroupService groupService;

    @Autowired
    private UserMeetService userMeetService;

    @Autowired
    private UserGroupInviteService userGroupInviteService;

    @Autowired
    public GroupController(GroupService service) {
        super(service);
        this.groupService = service;
    }


    @RequestMapping(value = "/{id}/meets", method = RequestMethod.GET)
    @ResponseBody
    public List getGroupMeetsByGroupIdAndTime(@PathVariable long id, @RequestParam(value = "start", required = false) Long start, @RequestParam(value = "end", required = false) Long end) {
        checkIsNotFoundById(id);
        if (start == null || end == null) {
            return groupService.getMeetsByGroupId(id);
        }
        return groupService.getMeetsByGroupIdAndTimePeriod(id, start, end);
    }


    @RequestMapping(value = "/{id}/usermeets", method = RequestMethod.GET)
    @ResponseBody
    public List getUserMeetsByGroupIdAndTime(@PathVariable long id, @RequestParam(value = "start", required = false) Long start, @RequestParam(value = "end", required = false) Long end, @CurrentUser User user) {
        checkIsNotFoundById(id);
        if (start == null || end == null) {
            throw new BadRequestException();
        }
        return userMeetService.getUserMeetsByGroupIdAndTimePeriodAndUserId(id, user.getUserId(), start, end);
    }

    @RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
    public List getGroupUsersById(@PathVariable long id) {
        checkIsNotFoundById(id);
        return groupService.getUsersByGroupId(id);
    }

    @RequestMapping(value = "/{id}/usergroups", method = RequestMethod.GET)
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
        return getAndCheckIsNotFoundById(groupId).getChat();
    }

    @RequestMapping(value = "/{id}/invites", method = RequestMethod.GET)
    public List getInvitedUsersByGroupId(@PathVariable("id") long groupId, @CurrentUser User user) {
        return userGroupInviteService.getInvitedUsersByGroupId(groupId);
    }

}
