/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.service.entity.impl;

import java.util.ArrayList;
import java.util.List;

import com.promeets.model.entity.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promeets.model.repository.MeetInfoRepository;
import com.promeets.model.service.entity.MeetInfoService;
import com.promeets.model.service.entity.UserMeetService;
import com.promeets.model.service.notification.MeetInfoNotificationService;

@Service
public class MeetInfoServiceImpl extends BaseNotifiedServiceImpl<UserMeetInfo, UserMeetPK>
        implements MeetInfoService {
    private static final Logger log = Logger.getLogger(MeetInfoServiceImpl.class);

    private MeetInfoRepository meetInfoRepository;
    private MeetInfoNotificationService meetInfoNotificationService;

    @Autowired
    private UserMeetService userMeetService;

    @Autowired
    public MeetInfoServiceImpl(MeetInfoRepository repository, MeetInfoNotificationService notificationService) {
        super(repository, notificationService);
        this.meetInfoRepository = repository;
        this.meetInfoNotificationService = notificationService;
    }

    @Override
    public List<UserMeetInfo> getByMeetId(Long meetId) {
        return (List<UserMeetInfo>) meetInfoRepository.getByMeetId(meetId);
    }

    @Override
    public List<UserMeetInfo> getOnlineByMeetId(Long meetId) {
        return (List<UserMeetInfo>) meetInfoRepository.getOnlineByMeetId(meetId);
    }

    @Override
    @Transactional
    public void deleteByMeetId(Long meetId) {
        meetInfoRepository.deleteByMeetId(meetId);
    }

    @Override
    @Transactional
    public List<UserMeetInfo> createByMeet(Meet meet) {
        List<UserMeetInfo> meetInfos = new ArrayList<>();
        for (UserMeet userMeet : userMeetService.getUserMeetsByMeetId(meet.getMeetId())) {
            UserMeetInfo meetInfo = new UserMeetInfo();
            meetInfo.setUserMeetPK(userMeet.getUserMeetPK());
            meetInfo.setMeet(userMeet.getMeet());
            meetInfo.setUser(userMeet.getUser());
            meetInfo.setOnline(false);
            meetInfo.setConnected(false);
            meetInfos.add(meetInfo);
        }
        return (List<UserMeetInfo>) meetInfoRepository.save(meetInfos);
    }

    @Override
    public void createMeetInfoByUserAndMeet(User user, Meet meet) {
        UserMeetInfo userMeetInfo = new UserMeetInfo();
        userMeetInfo.setUser(user);
        userMeetInfo.setMeet(meet);
        userMeetInfo.setOnline(false);
        userMeetInfo.setConnected(false);
        meetInfoRepository.save(userMeetInfo);
    }
}
