/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.*;

import java.sql.Timestamp;

/**
 * @author MDay
 */
@Transactional
public interface GroupRepository extends CrudRepository<Group, Long> {

    @Cacheable(value = "groupMeetsById")
    @Query("select meet from Meet meet where  meet.group.groupId=(:groupId) order by meet.time")
    Iterable<Meet> getMeetsByGroupId(@Param("groupId") Long id);

    @Cacheable(value = "groupMeetsByTimePeriod")
    @Query("select meet from Meet meet where  meet.group.groupId=(:groupId) and meet.time>=(:start) and meet.time<=(:end) order by meet.time")
    Iterable<Meet> getMeetsByGroupIdAndTimePeriod(@Param("groupId") long groupId, @Param("start") long start, @Param("end") long end);

    @Modifying
    @Query("delete from Meet meet where  meet.group.groupId=(:groupId)")
    Iterable<Meet> deleteMeetsByGroupId(@Param("groupId") Long id);

    @Query("select groupType from GroupType groupType")
    Iterable<GroupType> getGroupTypes();

}
