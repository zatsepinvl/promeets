package com.promeets.model.service.entity;

import com.promeets.model.entity.UserGroupInvite;
import com.promeets.model.entity.User;
import com.promeets.model.entity.UserGroupPK;

import java.util.List;

/**
 * Created by Vladimir on 18.05.2016.
 */
public interface UserGroupInviteService extends BaseService<UserGroupInvite, UserGroupPK> {

    List<UserGroupInvite> getUserGroupInvitesByUserId(long userId);

    List<UserGroupInvite> getUserInvitesByGroupId(long groupId);

    List<User> getInvitedUsersByGroupId(long groupId);

    void deleteUserGroupInviteByUserIdAndGroupId(long userId, long groupId);
}
