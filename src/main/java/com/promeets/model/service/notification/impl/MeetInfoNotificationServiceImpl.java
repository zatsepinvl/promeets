/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.service.notification.impl;

import java.util.List;

import com.promeets.model.entity.UserMeetInfo;
import com.promeets.model.repository.MeetInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promeets.controller.StompNotificationController;
import com.promeets.model.service.notification.MeetInfoNotificationService;
import com.promeets.model.service.notification.Notification;

/**
 * @author Alex
 */

@Service
public class MeetInfoNotificationServiceImpl extends BaseNotificationServiceImpl<UserMeetInfo> implements MeetInfoNotificationService {
    @Autowired
    StompNotificationController notificationController;
    @Autowired
    MeetInfoRepository meetInfoRepository;

    @Override
    protected void onAction(UserMeetInfo entity, Notification.Action action) {
        List<UserMeetInfo> userMeetInfo = (List<UserMeetInfo>) meetInfoRepository.getOnlineByMeetId(entity.getMeet().getMeetId());
        for (UserMeetInfo meetInfo : userMeetInfo) {
            if (entity.getUser().getUserId() != meetInfo.getUser().getUserId()) {
                Notification notification = new Notification(entity.getClass(), action,
                        entity.getMeet().getMeetId());
                notification.setData(entity);
                notificationController.notifyUser(notification, meetInfo.getUser());
            }
        }
    }
}
