package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.model.entity.MeetTask;
import ru.unc6.promeets.model.repository.TaskRepository;
import ru.unc6.promeets.model.service.entity.TaskService;
import ru.unc6.promeets.model.service.notification.TaskNotificationService;

/**
 * Created by Vladimir on 06.04.2016.
 */
@Service
public class TaskServiceImpl extends BaseNotificatedServiceImpl<MeetTask, Long> implements TaskService {

    @Autowired
    public TaskServiceImpl(TaskRepository repository, TaskNotificationService notificationService) {
        super(repository, notificationService);
    }
}
