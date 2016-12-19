package com.promeets.model.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promeets.controller.StompNotificationController;
import com.promeets.model.entity.Card;
import com.promeets.model.entity.UserMeet;
import com.promeets.model.service.entity.UserMeetService;
import com.promeets.model.service.notification.CardNotificationService;
import com.promeets.model.service.notification.Notification;

@Service
public class CardNotificationServiceImpl extends BaseNotificationServiceImpl<Card>
        implements CardNotificationService {

    @Autowired
    private StompNotificationController notificationController;

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
            if (!userMeet.getUser().equals(entity.getUser())) {
                notificationController.notifyUser(notification, userMeet.getUser());
            }
        }
    }
}
