package com.promeets.model.repository;

import com.promeets.model.entity.Meet;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MeetRepository extends CrudRepository<Meet, Long> {

    @Query(value = "select meet from Meet meet where meet.group.groupId=(:groupId)")
    Iterable<Meet> getMeetsByGroupId(@Param("groupId") long groupId);

    @Query("select meet from Meet meet where  meet.group.groupId=(:groupId) and meet.time>=(:start) and meet.time<=(:end) order by meet.time")
    Iterable<Meet> getMeetsByGroupIdAndTimePeriod(@Param("groupId") long groupId, @Param("start") long start, @Param("end") long end);

    @Modifying
    @Transactional
    @Query("delete from Meet meet where meet.group.groupId=(:groupId)")
    void deleteMeetsByGroupId(@Param("groupId") long groupId);
}
