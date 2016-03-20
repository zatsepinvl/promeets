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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.model.entity.Group;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserGroup;
import ru.unc6.promeets.model.service.GroupService;

/**
 * @author MDay
 */

@RestController
public class GroupController {
    private static final Logger log = Logger.getLogger(MeetController.class);

    @Autowired
    GroupService groupService;

    @RequestMapping(value = "api/groups", method = RequestMethod.GET)
    public ResponseEntity<List<Group>> getGroups() {
        List<Group> groups = groupService.getAll();

        if (groups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @RequestMapping(value = "api/groups/{id}", method = RequestMethod.GET)
    public ResponseEntity<Group> getGroupById(@PathVariable long id) {
        Group group = groupService.getById(id);

        if (group == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @RequestMapping(value = "api/groups", method = RequestMethod.POST)
    public ResponseEntity<Void> createGroup(@RequestBody Group group) {
        if (group == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        groupService.save(group);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "api/groups/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateGroup(@RequestBody Group group, @PathVariable long id) {
        if (groupService.getById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        group.setGroupId(id);
        groupService.save(group);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "api/groups/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> updateGroup(@PathVariable long id) {
        if (groupService.getById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        groupService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "api/groups/{id}/meets", method = RequestMethod.GET)
    public ResponseEntity<List<Meet>> getGroupMeetsById(@PathVariable long id) {
        List<Meet> meets = groupService.getMeetsByGroupId(id);
        if (meets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(meets, HttpStatus.OK);
    }

    @RequestMapping(value = "api/groups/{id}/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getGroupUsersById(@PathVariable long id) {
        List<User> users = groupService.getUsersByGroupId(id);

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "api/groups/{id}/user_groups", method = RequestMethod.GET)
    public ResponseEntity<List<UserGroup>> getGroupUserGroupsById(@PathVariable long id) {
        List<UserGroup> userGroups = groupService.getUserGroupsByGroupId(id);

        if (userGroups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userGroups, HttpStatus.OK);
    }

    @RequestMapping(value = "api/group_types", method = RequestMethod.GET)
    public ResponseEntity<List> getTypes() {
        return new ResponseEntity<List>(groupService.getGroupTypes(), HttpStatus.OK);
    }

}
