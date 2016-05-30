/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.controller.StompNotificationController;
import ru.unc6.promeets.model.entity.MeetTask;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserMeet;
import ru.unc6.promeets.model.service.entity.UserMeetService;
import ru.unc6.promeets.model.service.entity.UserService;
import ru.unc6.promeets.model.service.notification.Notification;
import ru.unc6.promeets.model.service.notification.TaskNotificationService;

/**
 * @author MDay
 */
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
