/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.entity;

import java.util.List;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.UserMeetInfo;
import ru.unc6.promeets.model.entity.UserMeetPK;

/**
 *
 * @author Alex
 */
public interface MeetInfoService extends BaseService<UserMeetInfo, UserMeetPK>{
    public List<UserMeetInfo> getByMeetId(Long meetId);
    public List<UserMeetInfo> getOnlineByMeetId(Long meetId);
    public void deleteByMeetId(Long meetId);
    public List<UserMeetInfo> createByMeet(Meet meet);
}
