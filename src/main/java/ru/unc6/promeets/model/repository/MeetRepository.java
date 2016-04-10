package ru.unc6.promeets.model.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.*;

public interface MeetRepository extends CrudRepository<Meet, Long> {

    @Cacheable
    @Query("select meetNote from MeetNote meetNote where  meetNote.meet.meetId=(:meetId)")
    Iterable<MeetNote> getMeetNotesByMeetId(@Param("meetId") long id);

    @Cacheable
    @Query("select meetAim from MeetTask meetAim where  meetAim.meet.meetId=(:meetId)")
    Iterable<MeetTask> getMeetTasksByMeetId(@Param("meetId") long id);

    @Cacheable
    @Modifying
    @Query("delete from MeetTask meetAim where meetAim.meet.meetId=(:meetId)")
    void deleteAllAimsById(@Param("meetId") long id);

    @Modifying
    @Query("delete from MeetNote meetNote where meetNote.meet.meetId=(:meetId)")
    void deleteAllNotesById(@Param("meetId") long id);

}
