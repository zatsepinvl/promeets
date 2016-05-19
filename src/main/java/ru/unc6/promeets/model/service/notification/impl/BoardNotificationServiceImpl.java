package ru.unc6.promeets.model.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.controller.NotificationController;
import ru.unc6.promeets.model.entity.Board;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserMeet;
import ru.unc6.promeets.model.repository.UserMeetRepository;
import ru.unc6.promeets.model.service.entity.UserService;
import ru.unc6.promeets.model.service.notification.BoardNotificationService;
import ru.unc6.promeets.model.service.notification.Notification;

/**
 * Created by Vladimir on 23.04.2016.
 */
@Service
public class BoardNotificationServiceImpl extends BaseNotificationServiceImpl<Board> implements BoardNotificationService {

    @Autowired
    private NotificationController notificationController;

    @Autowired
    private UserMeetRepository userMeetRepository;

    @Autowired
    private UserService userService;

    @Override
    protected void onAction(Board entity, Notification.Action action) {
        Notification notification = new Notification()
                .setAction(action)
                .setId(entity.getBoardId())
                .setEntity(entity.getClass().getSimpleName().toLowerCase())
                .setData(entity);
        User currentUser = userService.getCurrentAuthenticatedUser();
        for (UserMeet userMeet : userMeetRepository.getUserMeetsByMeetId(entity.getMeet().getMeetId())) {
            if (currentUser != null && userService.getCurrentAuthenticatedUser().getUserId() == userMeet.getUser().getUserId()) {
            } else {
                notificationController.sendNotificationToUser(notification, userMeet.getUser());
            }
        }
    }
}
