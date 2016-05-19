package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.*;
import ru.unc6.promeets.model.repository.MeetRepository;
import ru.unc6.promeets.model.repository.UserChatRepository;
import ru.unc6.promeets.model.repository.UserGroupRepository;
import ru.unc6.promeets.model.repository.UserMeetRepository;
import ru.unc6.promeets.model.service.entity.*;

import java.util.List;

@Service
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroup, UserGroupPK>
        implements UserGroupService {

    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserChatService userChatService;
    @Autowired
    private UserMessageService userMessageService;
    @Autowired
    private UserMeetService userMeetService;

    @Autowired
    private GroupService groupService;

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
    @Transactional
    public void deleteUserGroupsByGroupId(long id) {
        userGroupRepository.deleteUserGroupsByGroupId(id);
    }

    @Override
    public UserGroup create(UserGroup entity) {
        if (groupService.getById(entity.getGroup().getGroupId()) == null) {
            groupService.create(entity.getGroup());
        } else {
            createUserChat(entity.getUser(), entity.getGroup());
            createUserMessages(entity.getUser(), entity.getGroup());
            createUserMeets(entity.getUser(), entity.getGroup());
        }
        return super.create(entity);
    }

    @Async
    private void createUserChat(User user, Group group) {
        userChatService.createUserChatByUserAndChat(user, group.getChat());
    }

    @Async
    private void createUserMeets(User user, Group group) {
        userMeetService.createUserMeetsByUserAndGroup(user, group);
    }

    @Async
    private void createUserMessages(User user, Group group) {
        userMessageService.createUserMessagesByUserAndChat(user, group.getChat());
    }
}
