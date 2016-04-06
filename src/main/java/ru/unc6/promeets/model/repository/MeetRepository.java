package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.*;

public interface MeetRepository extends CrudRepository<Meet, Long> {
    @Query("select userMeet from UserMeet userMeet where  userMeet.userMeetPK.meet.meetId=(:meetId)")
    Iterable<UserMeet> getAllUserMeetsByMeetId(@Param("meetId") Long id);

    @Query("select userMeetPK.user from UserMeet userMeet where  userMeet.userMeetPK.meet.meetId=(:meetId)")
    Iterable<User> getAllUsersByMeetId(@Param("meetId") Long id);

    @Query("select meetNote from MeetNote meetNote where  meetNote.meet.meetId=(:meetId)")
    Iterable<MeetNote> getAllMeetNotesByMeetId(@Param("meetId") Long id);

    @Query("select meetAim from MeetTask meetAim where  meetAim.meet.meetId=(:meetId)")
    Iterable<MeetTask> getAllMeetAimsByMeetId(@Param("meetId") Long id);

    @Modifying
    @Query("delete from MeetTask meetAim where meetAim.meet.meetId=(:meetId)")
    void deleteAllAimsById(@Param("meetId") Long id);
    
    @Modifying
    @Query("delete from MeetNote meetNote where meetNote.meet.meetId=(:meetId)")
    void deleteAllNotesById(@Param("meetId") Long id);
    
    @Modifying
    @Query("delete from UserMeet userMeet where userMeet.userMeetPK.meet.meetId=(:meetId)")
    void deleteAllUserMeetsById(@Param("meetId") Long id);
}
