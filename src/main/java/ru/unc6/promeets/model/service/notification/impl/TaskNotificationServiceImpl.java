/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.controller.AppSTOMPController;
import ru.unc6.promeets.model.entity.MeetTask;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.repository.MeetRepository;
import ru.unc6.promeets.model.repository.UserMeetRepository;
import ru.unc6.promeets.model.service.notification.Notification;
import ru.unc6.promeets.model.service.notification.TaskNotificationService;

/**
 * @author MDay
 */
@Service
public class TaskNotificationServiceImpl implements TaskNotificationService {
    @Autowired
    AppSTOMPController appSTOMPController;
    @Autowired
    MeetRepository meetRepository;
    @Autowired
    UserMeetRepository userMeetRepository;

    @Override
    public void onCreate(MeetTask entity) {
        onAction(entity, Notification.Action.CREATE);
    }

    @Override
    public void onUpdate(MeetTask entity) {
        onAction(entity, Notification.Action.UPDATE);
    }

    @Override
    public void onDelete(MeetTask entity) {
        onAction(entity, Notification.Action.DELETE);
    }

    private void onAction(MeetTask task, Notification.Action action) {
        Notification notification = new Notification(task.getClass(), action, task.getTaskId());
        for (User user : userMeetRepository.getUsersByMeetId(task.getMeet().getMeetId())) {
            appSTOMPController.sendNotificationToUser(notification, user);
        }
    }

}
