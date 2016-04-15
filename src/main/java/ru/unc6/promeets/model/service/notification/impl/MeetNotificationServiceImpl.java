/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.controller.AppSTOMPController;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.repository.UserMeetRepository;
import ru.unc6.promeets.model.service.notification.MeetNotificationService;
import ru.unc6.promeets.model.service.notification.Notification;

/**
 * @author MDay
 */
@Service
public class MeetNotificationServiceImpl extends BaseNotificationServiceImpl<Meet>
        implements MeetNotificationService {

    @Autowired
    AppSTOMPController appSTOMPController;

    @Autowired
    UserMeetRepository userMeetRepository;

    @Override
    protected void onAction(Meet entity, Notification.Action action) {
        Notification notification = new Notification(entity.getClass(), action, entity.getMeetId());
        for (User user : userMeetRepository.getUsersByMeetId(entity.getMeetId())) {
            if (entity.getAdmin().getUserId() != user.getUserId()) {
                appSTOMPController.sendNotificationToUser(notification, user);
            }
        }
    }


}
