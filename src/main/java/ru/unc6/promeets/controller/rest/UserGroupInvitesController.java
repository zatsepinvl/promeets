package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserGroupInvite;
import ru.unc6.promeets.model.service.entity.GroupService;
import ru.unc6.promeets.model.service.entity.UserGroupInviteService;
import ru.unc6.promeets.security.CurrentUser;

import java.util.List;

/**
 * Created by Vladimir on 18.05.2016.
 */
@RestController
@RequestMapping("/api/users/group_invites")
public class UserGroupInvitesController {

    @Autowired
    private UserGroupInviteService userGroupInviteService;

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/{groupId}", method = RequestMethod.GET)
    public List getUserGroupInvitesByGroup(@PathVariable("groupId") long groupId, @CurrentUser User user) {
        return userGroupInviteService.getUserInvitesByGroupId(groupId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List getUserGroupInvitesByUser(@CurrentUser User user) {
        return userGroupInviteService.getUserGroupInvitesByUserId(user.getUserId());
    }

    @RequestMapping(method = RequestMethod.POST)
    public UserGroupInvite inviteUser(@RequestBody UserGroupInvite userGroupInvite) {
        return userGroupInviteService.create(userGroupInvite);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public UserGroupInvite updateUserGroupByGroupId(@PathVariable("id") long groupId, @RequestBody UserGroupInvite userGroupInvite) {
        return userGroupInviteService.update(userGroupInvite);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUserGroupInviteByGroupId(@PathVariable("id") long groupId, @CurrentUser User user) {
        userGroupInviteService.deleteUserGroupInviteByUserIdAndGroupId(user.getUserId(), groupId);
    }
}
