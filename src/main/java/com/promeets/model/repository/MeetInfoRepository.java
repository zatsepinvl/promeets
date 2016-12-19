/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.repository;

import com.promeets.model.entity.UserMeetInfo;
import com.promeets.model.entity.UserMeetPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MeetInfoRepository extends CrudRepository<UserMeetInfo, UserMeetPK> {
    @Query("select meetInfo from UserMeetInfo meetInfo where meetInfo.userMeetPK.meet.meetId=(:meetId) order by meetInfo.id.user.userId")
    Iterable<UserMeetInfo> getByMeetId(@Param("meetId") Long id);

    @Query("select meetInfo from UserMeetInfo meetInfo where meetInfo.userMeetPK.meet.meetId=(:meetId) and meetInfo.online=true order by meetInfo.id.user.userId")
    Iterable<UserMeetInfo> getOnlineByMeetId(@Param("meetId") Long id);

    @Modifying
    @Transactional
    @Query("delete from UserMeetInfo meetInfo where meetInfo.userMeetPK.meet.meetId=(:meetId)")
    void deleteByMeetId(@Param("meetId") long id);
}
