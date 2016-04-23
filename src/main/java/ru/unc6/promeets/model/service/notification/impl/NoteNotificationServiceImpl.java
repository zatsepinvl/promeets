package ru.unc6.promeets.model.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.controller.AppSTOMPController;
import ru.unc6.promeets.model.entity.MeetNote;
import ru.unc6.promeets.model.entity.UserMeet;
import ru.unc6.promeets.model.service.entity.UserMeetService;
import ru.unc6.promeets.model.service.notification.NoteNotificationService;
import ru.unc6.promeets.model.service.notification.Notification;

@Service
public class NoteNotificationServiceImpl extends BaseNotificationServiceImpl<MeetNote>
        implements NoteNotificationService {
    @Autowired
    private AppSTOMPController appSTOMPController;

    @Autowired
    private UserMeetService userMeetService;

    @Override
    protected void onAction(MeetNote entity, Notification.Action action) {
        Notification notification = new Notification()
                .setAction(action)
                .setId(entity.getNoteId())
                .setData(entity)
                .setEntity(entity.getClass().getSimpleName().toLowerCase());
        for (UserMeet userMeet : userMeetService.getUserMeetsByMeetId(entity.getMeet().getMeetId())) {
            if (userMeet.getUser().getUserId() != entity.getUser().getUserId()) {
                appSTOMPController.sendNotificationToUser(notification, userMeet.getUser());
            }
        }
    }
}
