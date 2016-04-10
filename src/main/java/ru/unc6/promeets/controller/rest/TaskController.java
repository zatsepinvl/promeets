package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.model.entity.MeetTask;
import ru.unc6.promeets.model.service.entity.MessageService;
import ru.unc6.promeets.model.service.entity.TaskService;
import ru.unc6.promeets.model.service.notify.MessageNotifyService;
import ru.unc6.promeets.model.service.notify.TaskNotifyService;

/**
 * Created by Vladimir on 06.04.2016.
 */
@RestController
@RequestMapping(value = "/api/tasks")
public class TaskController extends BaseRestController<MeetTask> {

    private TaskService taskService;
    private TaskNotifyService taskNotifyService;

    @Autowired
    public TaskController(TaskService service, TaskNotifyService notifyService) {
        super(service,notifyService);
        this.taskService = service;
        this.taskNotifyService = notifyService;
    }
}
