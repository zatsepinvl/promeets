package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.UserMeet;

public interface MeetRepository extends CrudRepository<Meet, Long> {
    @Query("select userMeet from UserMeet userMeet where  userMeet.userMeetPK.meet.meetId=(:meet_id)")
    Iterable<UserMeet> getAllUserMeetsByMeetId(@Param("meet_id") Long id);
}
