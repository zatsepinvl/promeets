/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.service.entity;

import java.util.List;

import com.promeets.model.entity.UserMeetInfo;
import com.promeets.model.entity.UserMeetPK;
import com.promeets.model.entity.Meet;
import com.promeets.model.entity.User;

public interface MeetInfoService extends BaseService<UserMeetInfo, UserMeetPK> {
    List<UserMeetInfo> getByMeetId(Long meetId);

    List<UserMeetInfo> getOnlineByMeetId(Long meetId);

    void deleteByMeetId(Long meetId);

    List<UserMeetInfo> createByMeet(Meet meet);

    void createMeetInfoByUserAndMeet(User user, Meet meet);
}
