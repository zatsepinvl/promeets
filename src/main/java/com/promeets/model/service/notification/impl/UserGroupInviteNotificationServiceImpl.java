package com.promeets.model.service.notification.impl;

import com.promeets.model.entity.UserGroupInvite;
import com.promeets.model.service.notification.UserGroupInviteNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promeets.controller.StompNotificationController;
import com.promeets.model.entity.User;
import com.promeets.model.service.entity.GroupService;
import com.promeets.model.service.notification.Notification;

/**
 * Created by Vladimir on 19.05.2016.
 */
@Service
public class UserGroupInviteNotificationServiceImpl extends BaseNotificationServiceImpl<UserGroupInvite>
        implements UserGroupInviteNotificationService {

    @Autowired
    private StompNotificationController notificationController;

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
            notificationController.notifyUser(notification, user);
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
                notificationController.notifyUser(notification, user);
            }
        }
        notificationController.notifyUser(notification, entity.getUser());
    }
}
