/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.notify;

import java.util.List;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.service.notify.Notify.Action;

/**
 *
 * @author MDay
 */
public interface NotifyService
{
   public void sendNotifyMessage(User user, Class entityClass, Long entityId, Action action);
   public void sendNotifyMessage(List<User> users, Class entityClass, Long entityId, Action action); 
}
