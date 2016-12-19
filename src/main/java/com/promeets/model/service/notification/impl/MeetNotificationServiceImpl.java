/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.service.notification.impl;

import com.promeets.model.repository.UserMeetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promeets.controller.StompNotificationController;
import com.promeets.model.entity.Meet;
import com.promeets.model.entity.User;
import com.promeets.model.service.entity.UserService;
import com.promeets.model.service.notification.MeetNotificationService;
import com.promeets.model.service.notification.Notification;

@Service
public class MeetNotificationServiceImpl extends BaseNotificationServiceImpl<Meet>
        implements MeetNotificationService {

    @Autowired
    StompNotificationController notificationController;

    @Autowired
    UserMeetRepository userMeetRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onCreate(Meet entity) {
        //
    }

    @Override
    public void onDelete(Meet entity) {
        //
    }

    @Override
    protected void onAction(Meet entity, Notification.Action action) {
        User currentUser = userService.getCurrentAuthenticatedUser();
        Notification notification = new Notification()
                .setEntity(entity.getClass().getSimpleName().toLowerCase())
                .setId(entity.getMeetId())
                .setAction(action)
                .setData(entity);
        for (User user : userMeetRepository.getUsersByMeetId(entity.getMeetId())) {
            if (!currentUser.equals(user)) {
                notificationController.notifyUser(notification, user);
            }
        }
    }


}
