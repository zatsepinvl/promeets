package com.promeets.model.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promeets.controller.StompNotificationController;
import com.promeets.model.entity.MeetNote;
import com.promeets.model.entity.User;
import com.promeets.model.entity.UserMeet;
import com.promeets.model.service.entity.UserMeetService;
import com.promeets.model.service.entity.UserService;
import com.promeets.model.service.notification.NoteNotificationService;
import com.promeets.model.service.notification.Notification;

@Service
public class NoteNotificationServiceImpl extends BaseNotificationServiceImpl<MeetNote>
        implements NoteNotificationService {
    @Autowired
    private StompNotificationController notificationController;

    @Autowired
    private UserMeetService userMeetService;

    @Autowired
    private UserService userService;

    @Override
    protected void onAction(MeetNote entity, Notification.Action action) {
        Notification notification = new Notification()
                .setAction(action)
                .setId(entity.getNoteId())
                .setData(entity)
                .setEntity(entity.getClass().getSimpleName().toLowerCase());
        User user = userService.getCurrentAuthenticatedUser();
        for (UserMeet userMeet : userMeetService.getUserMeetsByMeetId(entity.getMeet().getMeetId())) {
            if (userMeet.getUser().getUserId() != user.getUserId()) {
                notificationController.notifyUser(notification, userMeet.getUser());
            }
        }
    }
}
