package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.controller.exception.ForbiddenRequestException;
import ru.unc6.promeets.controller.exception.ForbiddenResponseErrorMessage;
import ru.unc6.promeets.controller.exception.NotFoundException;
import ru.unc6.promeets.controller.exception.NotFoundResponseErrorMessage;
import ru.unc6.promeets.model.entity.Group;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserGroup;
import ru.unc6.promeets.model.entity.UserGroupPK;
import ru.unc6.promeets.model.service.entity.GroupService;
import ru.unc6.promeets.model.service.entity.UserGroupInviteService;
import ru.unc6.promeets.model.service.entity.UserGroupService;
import ru.unc6.promeets.security.CurrentUser;

import java.util.List;

@RestController
@RequestMapping("/api/users/groups")
public class UserGroupController extends BaseUserRestController<UserGroup, UserGroupPK, Group> {

    @Autowired
    private UserGroupInviteService userGroupInviteService;

    private UserGroupService userGroupService;

    private GroupService groupService;

    @Autowired
    public UserGroupController(GroupService entityService, UserGroupService userEntityService) {
        super(entityService, userEntityService);
        this.groupService = entityService;
        this.userGroupService = userEntityService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List get(@CurrentUser User user) {
        return userGroupService.getUserGroupsByUserId(user.getUserId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public UserGroup update(@PathVariable("id") long id, @RequestBody UserGroup userGroup, @CurrentUser User user) {
        checkIsNotFound(id);
        checkHasOwnerAccess(userGroup, user);
        return userGroupService.update(userGroup);
    }

    @RequestMapping(method = RequestMethod.POST)
    public UserGroup create(@RequestBody UserGroup userGroup, @CurrentUser User user) {
        return userGroupService.create(userGroup);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id, @CurrentUser User user) {
        checkIsNotFound(id);
        UserGroup userGroup = userGroupService.getUserGroupByUserIdAndGroupId(id, user.getUserId());
        checkHasAccess(userGroup, user);
        userGroupService.delete(userGroup.getUserGroupPK());
    }

    @RequestMapping(value = "/{id}/invites", method = RequestMethod.GET)
    public List getUserInvitesByGroupId(@PathVariable("id") long groupId, @CurrentUser User user) {
        return userGroupInviteService.getUserInvitesByGroupId(groupId);
    }

    @Override
    protected void checkHasAccess(UserGroup userGroup, User user) {
        if (userGroup.getUser().equals(user)) {
            return;
        }
        if (userGroupService.getUserGroupByUserIdAndGroupId(user.getUserId(), userGroup.getGroup().getGroupId()) == null) {
            throw new ForbiddenRequestException().setResponseErrorMessage(new ForbiddenResponseErrorMessage());
        }
    }

    @Override
    protected void checkHasOwnerAccess(UserGroup userGroup, User user) {
        if (!userGroup.getUser().equals(user)) {
            throw new ForbiddenRequestException().setResponseErrorMessage(new ForbiddenResponseErrorMessage());
        }
    }

    private void checkIsNotFound(long id) {
        if (groupService.getById(id) == null) {
            throw new NotFoundException().setResponseErrorMessage(new NotFoundResponseErrorMessage());
        }
    }

}
