/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.entity;

import java.util.List;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.MeetInfo;

/**
 *
 * @author Alex
 */
public interface MeetInfoService extends BaseService<MeetInfo, Long>{
    public List<MeetInfo> getByMeetId(Long meetId);
    public List<MeetInfo> getOnlineByMeetId(Long meetId);
    public void deleteByMeetId(Long meetId);
    public List<MeetInfo> createByMeet(Meet meet);
}
