package ru.unc6.promeets.model.service.entity;

import ru.unc6.promeets.model.entity.MeetTask;

import java.util.List;

public interface TaskService extends BaseService<MeetTask,Long> {
    List<MeetTask> getTasksByMeetId(long meetId);
    void deleteTasksByMeetId(long meetId);
}
