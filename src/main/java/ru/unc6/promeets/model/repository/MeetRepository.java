package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.*;

public interface MeetRepository extends CrudRepository<Meet, Long> {
    @Query("select userMeet from UserMeet userMeet where  userMeet.userMeetPK.meet.meetId=(:meet_id)")
    Iterable<UserMeet> getAllUserMeetsByMeetId(@Param("meet_id") Long id);

    @Query("select userMeetPK.user from UserMeet userMeet where  userMeet.userMeetPK.meet.meetId=(:meet_id)")
    Iterable<User> getAllUsersByMeetId(@Param("meet_id") Long id);

    @Query("select meetNote from MeetNote meetNote where  meetNote.meet.meetId=(:meet_id)")
    Iterable<MeetNote> getAllMeetNotesByMeetId(@Param("meet_id") Long id);

    @Query("select meetAim from MeetAim meetAim where  meetAim.meet.meetId=(:meet_id)")
    Iterable<MeetAim> getAllMeetAimsByMeetId(@Param("meet_id") Long id);

    @Query("delete from MeetAim meetAim where meetAim.meet.meetId=(:meet_id)")
    void deleteAllAimsById(@Param("meet_id") Long id);
}
