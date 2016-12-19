package com.promeets.model.service.entity.impl;

import com.promeets.model.entity.User;
import com.promeets.model.entity.UserGroupInvite;
import com.promeets.model.entity.UserGroupPK;
import com.promeets.model.service.entity.UserGroupInviteService;
import com.promeets.model.service.notification.UserGroupInviteNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promeets.model.repository.UserGroupInviteRepository;

import java.util.List;

/**
 * Created by Vladimir on 18.05.2016.
 */
@Service
public class UserGroupInviteServiceImpl extends BaseNotifiedServiceImpl<UserGroupInvite, UserGroupPK>
        implements UserGroupInviteService {

    private UserGroupInviteRepository repository;

    @Autowired
    public UserGroupInviteServiceImpl(UserGroupInviteRepository repository, UserGroupInviteNotificationService notificationService) {
        super(repository, notificationService);
        this.repository = repository;
    }

    @Override
    public List<UserGroupInvite> getUserGroupInvitesByUserId(long userId) {
        return (List<UserGroupInvite>) repository.getUserGroupInvitesByUserId(userId);
    }

    @Override
    public List<UserGroupInvite> getUserInvitesByGroupId(long groupId) {
        return (List<UserGroupInvite>) repository.getUserInvitesByGroupId(groupId);
    }

    @Override
    public List<User> getInvitedUsersByGroupId(long groupId) {
        return (List<User>) repository.getInvitedUsersByGroupId(groupId);
    }

    @Override
    public void deleteUserGroupInviteByUserIdAndGroupId(long userId, long groupId) {
        repository.deleteUserGroupInviteByUserIdAndGroupId(userId, groupId);
    }

}
