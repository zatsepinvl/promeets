package ru.unc6.promeets.model.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.controller.NotificationController;
import ru.unc6.promeets.model.entity.Card;
import ru.unc6.promeets.model.entity.UserMeet;
import ru.unc6.promeets.model.service.entity.UserMeetService;
import ru.unc6.promeets.model.service.notification.CardNotificationService;
import ru.unc6.promeets.model.service.notification.Notification;

@Service
public class CardNotificationServiceImpl extends BaseNotificationServiceImpl<Card>
        implements CardNotificationService {

    @Autowired
    private NotificationController notificationController;

    @Autowired
    private UserMeetService userMeetService;

    @Override
    protected void onAction(Card entity, Notification.Action action) {
        Notification notification = new Notification()
                .setData(entity)
                .setId(entity.getCardId())
                .setAction(action)
                .setEntity(entity.getClass().getSimpleName().toLowerCase());
        for (UserMeet userMeet : userMeetService.getUserMeetsByMeetId(entity.getMeet().getMeetId())) {
            if (userMeet.getUser().getUserId() != entity.getUser().getUserId()) {
                notificationController.sendNotificationToUser(notification, userMeet.getUser());
            }
        }
    }
}
