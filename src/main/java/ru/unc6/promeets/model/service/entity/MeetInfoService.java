/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.entity;

import java.util.List;

import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserMeetInfo;
import ru.unc6.promeets.model.entity.UserMeetPK;

/**
 * @author Alex
 */

public interface MeetInfoService extends BaseService<UserMeetInfo, UserMeetPK> {
    List<UserMeetInfo> getByMeetId(Long meetId);

    List<UserMeetInfo> getOnlineByMeetId(Long meetId);

    void deleteByMeetId(Long meetId);

    List<UserMeetInfo> createByMeet(Meet meet);

    void createMeetInfoByUserAndMeet(User user, Meet meet);
}
