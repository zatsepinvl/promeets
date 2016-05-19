package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserGroupInvite;
import ru.unc6.promeets.model.entity.UserGroupPK;
import ru.unc6.promeets.model.repository.UserGroupInviteRepository;
import ru.unc6.promeets.model.service.entity.UserGroupInviteService;
import ru.unc6.promeets.model.service.notification.UserGroupInviteNotificationService;

import java.util.List;

/**
 * Created by Vladimir on 18.05.2016.
 */
@Service
public class UserGroupInviteServiceImpl extends BaseNotifiedServiceImpl<UserGroupInvite, UserGroupPK>
        implements UserGroupInviteService {

    private UserGroupInviteRepository repository;
    private UserGroupInviteNotificationService notificationService;

    @Autowired
    public UserGroupInviteServiceImpl(UserGroupInviteRepository repository, UserGroupInviteNotificationService notificationService) {
        super(repository, notificationService);
        this.repository = repository;
        this.notificationService = notificationService;
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
