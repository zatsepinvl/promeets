package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.controller.exception.ForbiddenRequestException;
import ru.unc6.promeets.controller.exception.ForbiddenResponseError;
import ru.unc6.promeets.controller.exception.NotFoundException;
import ru.unc6.promeets.controller.exception.NotFoundResponseError;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserGroup;
import ru.unc6.promeets.model.service.entity.GroupService;
import ru.unc6.promeets.model.service.entity.UserGroupService;
import ru.unc6.promeets.security.CurrentUser;

import java.util.List;

@RestController
@RequestMapping("/api/users/groups")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private GroupService groupService;

    @RequestMapping(method = RequestMethod.GET)
    public List get(@CurrentUser User user) {
        return userGroupService.getUserGroupsByUserId(user.getUserId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public UserGroup update(@PathVariable("id") long id, @RequestBody UserGroup userGroup, @CurrentUser User user) {
        checkIsNotFound(id);
        checkHasOwnerAccess(userGroup, user);
        return userGroupService.save(userGroup);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public UserGroup create(@PathVariable("id") long id, @RequestBody UserGroup userGroup, @CurrentUser User user) {
        checkIsNotFound(id);
        checkHasAccess(userGroup, user);
        return userGroupService.save(userGroup);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id, @CurrentUser User user) {
        checkIsNotFound(id);
        UserGroup userGroup = userGroupService.getUserGroupByUserIdAndGroupId(id, user.getUserId());
        checkHasAccess(userGroup, user);
        userGroupService.delete(userGroup.getUserGroupPK());
    }

    private void checkHasAccess(UserGroup userGroup, User user) {
        if (userGroup.getUser().equals(user)) {
            return;
        }
        if (userGroupService.getUserGroupByUserIdAndGroupId(user.getUserId(), userGroup.getGroup().getGroupId()) == null) {
            throw new ForbiddenRequestException().setResponseError(new ForbiddenResponseError());
        }
    }

    private void checkHasOwnerAccess(UserGroup userGroup, User user) {
        if (!userGroup.getUser().equals(user)) {
            throw new ForbiddenRequestException().setResponseError(new ForbiddenResponseError());
        }
    }

    private void checkIsNotFound(long id) {
        if (groupService.getById(id) == null) {
            throw new NotFoundException().setResponseError(new NotFoundResponseError());
        }
    }

}
