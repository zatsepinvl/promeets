/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.entity.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.MeetInfo;
import ru.unc6.promeets.model.entity.UserMeet;
import ru.unc6.promeets.model.repository.MeetInfoRepository;
import ru.unc6.promeets.model.repository.UserMeetRepository;
import ru.unc6.promeets.model.service.entity.MeetInfoService;
import ru.unc6.promeets.model.service.notification.MeetInfoNotificationService;

/**
 * @author Alex
 */

@Service
public class MeetInfoServiceImpl extends BaseNotifiedServiceImpl<MeetInfo, Long>
        implements MeetInfoService{
    private static final Logger log = Logger.getLogger(MeetInfoServiceImpl.class);

    private MeetInfoRepository meetInfoRepository;
    private MeetInfoNotificationService meetInfoNotificationService;

    @Autowired
    private UserMeetRepository userMeetRepository;
    
    @Autowired
    public MeetInfoServiceImpl(MeetInfoRepository repository, MeetInfoNotificationService notificationService) {
        super(repository, notificationService);
        this.meetInfoRepository = repository;
        this.meetInfoNotificationService = notificationService;
    }

    @Override
    public MeetInfo getById(Long id) {
        return meetInfoRepository.findOne(id);
    }

    @Override
    public List<MeetInfo> getByMeetId(Long meetId) {
        return (List<MeetInfo>) meetInfoRepository.getByMeetId(meetId);
    }

    @Override
    public List<MeetInfo> getOnlineByMeetId(Long meetId) {
        return (List<MeetInfo>) meetInfoRepository.getOnlineByMeetId(meetId);
    }

    @Override
    @Transactional
    public void deleteByMeetId(Long meetId) {
        meetInfoRepository.deleteByMeetId(meetId);
    }

    @Override
    @Transactional
    public List<MeetInfo> createByMeet(Meet meet) {
        List<MeetInfo> meetInfos = new ArrayList<>();
        for (UserMeet userMeet :userMeetRepository.getUserMeetsByMeetId(meet.getMeetId()))
        {
            MeetInfo meetInfo = new MeetInfo();
            meetInfo.setUserMeetPK(userMeet.getUserMeetPK());
            meetInfo.setMeet(userMeet.getMeet());
            meetInfo.setUser(userMeet.getUser());
            meetInfos.add(meetInfo);
        }
        
        return (List<MeetInfo>) meetInfoRepository.save(meetInfos);
    }
}
