/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promeets.controller.StompNotificationController;
import com.promeets.model.entity.MeetTask;
import com.promeets.model.entity.User;
import com.promeets.model.entity.UserMeet;
import com.promeets.model.service.entity.UserMeetService;
import com.promeets.model.service.entity.UserService;
import com.promeets.model.service.notification.Notification;
import com.promeets.model.service.notification.TaskNotificationService;

@Service
public class TaskNotificationServiceImpl extends BaseNotificationServiceImpl<MeetTask> implements TaskNotificationService {
    @Autowired
    private StompNotificationController notificationController;
    @Autowired
    private UserMeetService userMeetService;
    @Autowired
    private UserService userService;

    @Override
    protected void onAction(MeetTask task, Notification.Action action) {
        Notification notification = new Notification()
                .setData(task)
                .setEntity(task.getClass().getSimpleName().toLowerCase())
                .setId(task.getTaskId())
                .setAction(action);
        User currentUser = userService.getCurrentAuthenticatedUser();
        for (UserMeet userMeet : userMeetService.getUserMeetsByMeetId(task.getMeet().getMeetId())) {
            if (currentUser.getUserId() != userMeet.getUser().getUserId()) {
                notificationController.notifyUser(notification, userMeet.getUser());
            }
        }
    }

}
