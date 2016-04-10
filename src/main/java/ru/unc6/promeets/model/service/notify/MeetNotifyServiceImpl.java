/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.notify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.controller.AppSTOMPController;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.repository.MeetRepository;

/**
 *
 * @author MDay
 */
@Service
public class MeetNotifyServiceImpl implements MeetNotifyService
{
    @Autowired
    AppSTOMPController appSTOMPController;
    @Autowired
    MeetRepository meetRepository;

    @Override
    public void onCreate(Meet entity) {
        onAction(entity, Notification.Action.CREATE);
    }

    @Override
    public void onUpdate(Meet entity) {
        onAction(entity, Notification.Action.UPDATE);
    }

    @Override
    public void onDelete(Meet entity) {
        onAction(entity, Notification.Action.DELETE);
    }
    
    private void onAction (Meet meet, Notification.Action action) 
    {
        Notification notification = new Notification(meet.getClass(), action, meet.getMeetId());
        for (User user : meetRepository.getAllUsersByMeetId(meet.getMeetId()))
        {
            appSTOMPController.sendNotificationToUser(notification, user);
        }
    }
    
}
