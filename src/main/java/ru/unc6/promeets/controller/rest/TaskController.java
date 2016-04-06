package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.model.entity.MeetTask;
import ru.unc6.promeets.model.service.entity.TaskService;

/**
 * Created by Vladimir on 06.04.2016.
 */
@RestController
@RequestMapping(value = "/api/tasks")
public class TaskController extends BaseRestController<MeetTask> {

    @Autowired
    public TaskController(TaskService service) {
        super(service);
    }
}
