/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service;

import java.sql.Timestamp;
import java.util.List;

import ru.unc6.promeets.model.entity.*;

/**
 * @author MDay
 */
public interface GroupService extends BaseService<Group> {
    List<User> getUsersByGroupId(long id);

    List<UserGroup> getUserGroupsByGroupId(long id);

    List<Meet> getMeetsByGroupId(long id);

    List<Meet> getMeetsByGroupIdAndDay(long id, Timestamp date);

    List<Meet> getMeetsByGroupIdAndMonth(long id, Timestamp date);

    List<GroupType> getGroupTypes();
}
