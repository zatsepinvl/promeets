package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserMeet;
import ru.unc6.promeets.model.entity.UserMeetPK;
import ru.unc6.promeets.model.repository.UserGroupRepository;
import ru.unc6.promeets.model.repository.UserMeetRepository;
import ru.unc6.promeets.model.service.entity.UserMeetService;
import ru.unc6.promeets.model.service.notification.UserMeetNotificationService;

import java.util.ArrayList;
import java.util.List;
import ru.unc6.promeets.model.entity.MeetInfo;
import ru.unc6.promeets.model.service.entity.MeetInfoService;

@Service
public class UserMeetServiceImpl extends BaseNotificatedServiceImpl<UserMeet, UserMeetPK> 
        implements UserMeetService {

    private UserMeetNotificationService notificationService;
    private UserMeetRepository userMeetRepository;
    
    @Autowired
    private MeetInfoService meetInfoService;

    @Autowired
    private UserGroupRepository userGroupRepository;


    @Autowired
    public UserMeetServiceImpl(UserMeetRepository repository, UserMeetNotificationService notificationService) {
        super(repository, notificationService);
        this.userMeetRepository = repository;
        this.notificationService = notificationService;
    }
    
    @Override
    @Transactional
    public UserMeet update (UserMeet userMeet)
    {
        MeetInfo meetInfo = new MeetInfo();
        meetInfo.setUserMeetPK(userMeet.getUserMeetPK());
        meetInfo.setMeet(userMeet.getMeet());
        meetInfo.setUser(userMeet.getUser());
        meetInfo.setOnline(userMeet.isOnline());
        meetInfoService.update(meetInfo);
                
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
    public List<UserMeet> getNotViewedMeetsByUserId(long id) {
        return (List<UserMeet>) userMeetRepository.getNotViewedMeetsByUserId(id);
    }

    @Override
    public UserMeet getUserMeetByUserIdAndMeetId(long userId, long meetId) {
        return userMeetRepository.getUserMeetByUserIdAndMeetId(userId, meetId);
    }

    @Override
    @Transactional
    public void createUserMeetsByMeet(Meet meet) {
        List<UserMeet> userMeets = new ArrayList<>();
        long adminId = meet.getAdmin().getUserId();
        for (User user : userGroupRepository.getUsersByGroupId(meet.getGroup().getGroupId())) {
            UserMeet userMeet = new UserMeet();
            userMeet.setUser(user);
            userMeet.setMeet(meet);
            userMeet.setViewed(user.getUserId() == adminId);
            userMeets.add(userMeet);
        }
        userMeetRepository.save(userMeets);
    }

    @Override
    public void deleteUserMeetsByMeetId(long meetId) {
        userMeetRepository.deleteUserMeetsByMeetId(meetId);
    }
}
