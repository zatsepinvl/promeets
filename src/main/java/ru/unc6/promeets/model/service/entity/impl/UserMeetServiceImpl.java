package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.*;
import ru.unc6.promeets.model.repository.UserGroupRepository;
import ru.unc6.promeets.model.repository.UserMeetRepository;
import ru.unc6.promeets.model.service.entity.MeetService;
import ru.unc6.promeets.model.service.entity.UserMeetService;
import ru.unc6.promeets.model.service.notification.UserMeetNotificationService;

import java.util.ArrayList;
import java.util.List;

import ru.unc6.promeets.model.service.entity.MeetInfoService;

@Service

public class UserMeetServiceImpl extends BaseNotifiedServiceImpl<UserMeet, UserMeetPK>
        implements UserMeetService {

    private UserMeetNotificationService notificationService;
    private UserMeetRepository userMeetRepository;


    @Autowired
    private MeetInfoService meetInfoService;


    @Autowired
    private MeetService meetService;
    @Autowired
    private UserGroupRepository userGroupRepository;


    @Autowired
    public UserMeetServiceImpl(UserMeetRepository repository, UserMeetNotificationService notificationService) {
        super(repository, notificationService);
        this.userMeetRepository = repository;
        this.notificationService = notificationService;
    }

    @Override
    public UserMeet create(UserMeet entity) {
        if (meetService.getById(entity.getMeet().getMeetId()) == null) {
            meetService.create(entity.getMeet());
        }
        entity = userMeetRepository.save(entity);
        onMeetCreated(entity.getMeet());
        meetInfoService.createByMeet(entity.getMeet());
        return entity;
    }

    @Async
    private void onMeetCreated(Meet meet) {
        createUserMeetsByMeet(meet);
    }

    @Override
    @Transactional
    public void createUserMeetsByMeet(Meet meet) {
        long adminId = meet.getAdmin().getUserId();
        for (User user : userGroupRepository.getUsersByGroupId(meet.getGroup().getGroupId())) {
            if (user.getUserId() == adminId) {
                return;
            }
            meetInfoService.createMeetInfoByUserAndMeet(user, meet);
            UserMeet userMeet = new UserMeet();
            userMeet.setUser(user);
            userMeet.setMeet(meet);
            userMeet.setViewed(user.getUserId() == adminId);
            super.create(userMeet);
        }
    }

    @Override
    @Transactional
    public UserMeet update(UserMeet userMeet) {
        return super.update(userMeet);
    }

    @Override
    public List<UserMeet> getUserMeetsByMeetId(long id) {
        return (List<UserMeet>) userMeetRepository.getUserMeetsByMeetId(id);
    }

    @Override
    public List<UserMeet> getUserMeetsByUserId(long id) {
        return (List<UserMeet>) userMeetRepository.getUserMeetsByUserId(id);
    }

    @Override
    public List<UserMeet> getUserMeetsByUserIdAndTime(long userId, long start, long end) {
        return (List<UserMeet>) userMeetRepository.getUserMeetsByUserIdAndTimePeriod(userId, start, end);
    }

    @Override
    public List<UserMeet> getNotViewedMeetsByUserId(long id) {
        return (List<UserMeet>) userMeetRepository.getNotViewedMeetsByUserId(id);
    }

    @Override
    public UserMeet getUserMeetByUserIdAndMeetId(long userId, long meetId) {
        return userMeetRepository.getUserMeetByUserIdAndMeetId(userId, meetId);
    }

    @Override
    public List<UserMeet> getUserMeetsByGroupIdAndTimePeriodAndUserId(long groupId, long userId, long start, long end) {
        return (List<UserMeet>) userMeetRepository.getUserMeetsByGroupIdAndUserIdAndTimePeriod(groupId, userId, start, end);
    }


    @Override
    public void deleteUserMeetsByMeetId(long meetId) {
        userMeetRepository.deleteUserMeetsByMeetId(meetId);
    }

    @Override
    public void deleteUserMeetByUserIdAndMeetId(long userId, long meetId) {
        userMeetRepository.deleteUserMeetByUserIdAndMeetId(userId, meetId);
    }

    @Override
    public void createUserMeetsByUserAndGroup(User user, Group group) {
        long today = System.currentTimeMillis();
        for (Meet meet : meetService.getMeetsByGroupId(group.getGroupId())) {
            UserMeet userMeet = new UserMeet();
            userMeet.setMeet(meet);
            userMeet.setUser(user);
            if (meet.getTime() < today) {
                userMeet.setViewed(true);
            } else {
                userMeet.setViewed(false);
            }
            userMeetRepository.save(userMeet);
            meetInfoService.createMeetInfoByUserAndMeet(user, meet);
        }
    }
}
