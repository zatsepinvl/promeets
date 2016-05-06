package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.MeetTask;

import javax.transaction.Transactional;

public interface TaskRepository extends CrudRepository<MeetTask, Long> {

    @Query("select meetTask from MeetTask meetTask where  meetTask.meet.meetId=(:meetId) order by meetTask.taskId")
    Iterable<MeetTask> getMeetTasksByMeetId(@Param("meetId") long id);

    @Modifying
    @Transactional
    @Query("delete from MeetTask meetTask where meetTask.meet.meetId=(:meetId)")
    void deleteTasksByMeetId(@Param("meetId") long id);
}
