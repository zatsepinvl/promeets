/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.notification.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.controller.AppSTOMPController;
import ru.unc6.promeets.model.entity.MeetInfo;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.repository.MeetInfoRepository;
import ru.unc6.promeets.model.repository.UserMeetRepository;
import ru.unc6.promeets.model.service.notification.MeetInfoNotificationService;
import ru.unc6.promeets.model.service.notification.Notification;

/**
 *
 * @author Alex
 */

@Service
public class MeetInfoNotificationServiceImpl extends BaseNotificationServiceImpl<MeetInfo> implements MeetInfoNotificationService
{
    @Autowired
    AppSTOMPController appSTOMPController;
    @Autowired
    MeetInfoRepository meetInfoRepository;
    
    @Override
    protected void onAction(MeetInfo entity, Notification.Action action) {
        
        List<MeetInfo> meetInfos = (List<MeetInfo>) meetInfoRepository.getOnlineByMeetId(entity.getMeet().getMeetId());
        for (MeetInfo meetInfo : meetInfos) {
            Notification notification = new Notification(entity.getClass(), action, 
                    entity.getMeet().getMeetId());
            notification.setData(entity);
            appSTOMPController.sendNotificationToUser(notification, meetInfo.getUser());
        }
    }
}
