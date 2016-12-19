/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.service.entity;

import java.util.List;

import com.promeets.model.entity.*;

public interface GroupService extends BaseService<Group, Long> {
    List<User> getUsersByGroupId(long id);

    List<UserGroup> getUserGroupsByGroupId(long id);

    List<Meet> getMeetsByGroupId(long id);

    List<Meet> getMeetsByGroupIdAndTimePeriod(long id, long start, long end);

    List<GroupType> getGroupTypes();

    Group getGroupByChatId(long chatId);

}
