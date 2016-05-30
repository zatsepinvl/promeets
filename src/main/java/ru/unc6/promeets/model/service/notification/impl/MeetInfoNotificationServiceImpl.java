/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.notification.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.controller.StompNotificationController;
import ru.unc6.promeets.model.entity.UserMeetInfo;
import ru.unc6.promeets.model.repository.MeetInfoRepository;
import ru.unc6.promeets.model.service.notification.MeetInfoNotificationService;
import ru.unc6.promeets.model.service.notification.Notification;

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
