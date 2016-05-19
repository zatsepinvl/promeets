package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.*;


public interface MeetRepository extends CrudRepository<Meet, Long> {

    @Query(value = "select meet from Meet meet where meet.group.groupId=(:groupId)")
    Iterable<Meet> getMeetsByGroupId(@Param("groupId") long groupId);

    @Query("select meet from Meet meet where  meet.group.groupId=(:groupId) and meet.time>=(:start) and meet.time<=(:end) order by meet.time")
    Iterable<Meet> getMeetsByGroupIdAndTimePeriod(@Param("groupId") long groupId, @Param("start") long start, @Param("end") long end);
}
