package ru.unc6.promeets.model.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.controller.NotificationController;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserGroupInvite;
import ru.unc6.promeets.model.service.entity.GroupService;
import ru.unc6.promeets.model.service.notification.Notification;
import ru.unc6.promeets.model.service.notification.UserGroupInviteNotificationService;

/**
 * Created by Vladimir on 19.05.2016.
 */
@Service
public class UserGroupInviteNotificationServiceImpl extends BaseNotificationServiceImpl<UserGroupInvite>
        implements UserGroupInviteNotificationService {

    @Autowired
    private NotificationController notificationController;

    @Autowired
    private GroupService groupService;

    @Override
    public void onDelete(UserGroupInvite entity) {
        //
    }

    @Override
    public void onUpdate(UserGroupInvite entity) {
        Notification notification = new Notification()
                .setData(entity)
                .setAction(Notification.Action.UPDATE)
                .setEntity(entity.getClass().getSimpleName().toLowerCase())
                .setId(entity.getGroup().getGroupId());
        for (User user : groupService.getUsersByGroupId(entity.getGroup().getGroupId())) {
            notificationController.sendNotificationToUser(notification, user);
        }
    }

    @Override
    protected void onAction(UserGroupInvite entity, Notification.Action action) {
        Notification notification = new Notification()
                .setData(entity)
                .setAction(action)
                .setEntity(entity.getClass().getSimpleName().toLowerCase())
                .setId(entity.getGroup().getGroupId());
        for (User user : groupService.getUsersByGroupId(entity.getGroup().getGroupId())) {
            if (!user.equals(entity.getInviter())) {
                notificationController.sendNotificationToUser(notification, user);
            }
        }
        notificationController.sendNotificationToUser(notification, entity.getUser());
    }
}
