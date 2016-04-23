package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.model.entity.Group;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserGroup;
import ru.unc6.promeets.model.entity.UserGroupPK;
import ru.unc6.promeets.model.repository.UserGroupRepository;
import ru.unc6.promeets.model.service.entity.UserGroupService;

import java.util.List;

/**
 * Created by Vladimir on 12.04.2016.
 */
@Service
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroup, UserGroupPK>
        implements UserGroupService {

    private UserGroupRepository userGroupRepository;

    @Autowired
    public UserGroupServiceImpl(UserGroupRepository repository) {
        super(repository);
        this.userGroupRepository = repository;
    }

    @Override
    public UserGroup getUserGroupByUserIdAndGroupId(long userId, long groupId) {
        return userGroupRepository.getUserGroupByUserIdAndGroupId(userId, groupId);
    }

    @Override
    public List<User> getUsersByGroupId(long id) {
        return (List<User>) userGroupRepository.getUsersByGroupId(id);
    }

    @Override
    public List<UserGroup> getUserGroupsByGroupId(long id) {
        return (List<UserGroup>) userGroupRepository.getUserGroupsByGroupId(id);
    }

    @Override
    public List<Group> getGroupsByUserId(long id) {
        return (List<Group>) userGroupRepository.getGroupsByUserId(id);
    }

    @Override
    public List<UserGroup> getUserGroupsByUserId(long id) {
        return (List<UserGroup>) userGroupRepository.getUserGroupsByUserId(id);
    }

    @Override
    public void deleteUserGroupsByGroupId(long id) {
        userGroupRepository.deleteUserGroupsByGroupId(id);
    }
}
