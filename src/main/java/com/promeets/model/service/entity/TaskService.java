package com.promeets.model.service.entity;

import com.promeets.model.entity.MeetTask;

import java.util.List;

public interface TaskService extends BaseService<MeetTask,Long> {
    List<MeetTask> getTasksByMeetId(long meetId);
    void deleteTasksByMeetId(long meetId);
}
