package com.promeets.model.service.entity;

import com.promeets.model.entity.Group;
import com.promeets.model.entity.User;
import com.promeets.model.entity.UserGroup;
import com.promeets.model.entity.UserGroupPK;

import java.util.List;

/**
 * Created by Vladimir on 12.04.2016.
 */
public interface UserGroupService extends BaseService<UserGroup, UserGroupPK> {

    UserGroup getUserGroupByUserIdAndGroupId(long userId, long groupId);

    List<User> getUsersByGroupId(long id);

    List<UserGroup> getUserGroupsByGroupId(long id);

    Iterable<Group> getGroupsByUserId(long id);

    List<UserGroup> getUserGroupsByUserId(long id);

    void deleteUserGroupsByGroupId(long id);
}
