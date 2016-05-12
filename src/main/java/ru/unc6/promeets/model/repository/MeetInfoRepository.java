/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.MeetInfo;

/**
 *
 * @author Alex
 */
public interface MeetInfoRepository extends CrudRepository<MeetInfo, Long>
{
    @Query("select meetInfo from MeetInfo meetInfo where meetInfo.userMeetPK.meet.meetId=(:meetId)")
    Iterable<MeetInfo> getByMeetId(@Param("meetId") Long id);
    
    @Query("select meetInfo from MeetInfo meetInfo where meetInfo.userMeetPK.meet.meetId=(:meetId) and meetInfo.online=true")
    Iterable<MeetInfo> getOnlineByMeetId(@Param("meetId") Long id);
    
    @Modifying
    @Transactional
    @Query("delete from MeetInfo meetInfo where meetInfo.userMeetPK.meet.meetId=(:meetId)")
    void deleteByMeetId(@Param("meetId") long id);
}
