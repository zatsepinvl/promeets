package com.promeets.controller.rest;

import com.promeets.model.service.entity.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.promeets.model.entity.MeetTask;

/**
 * Created by Vladimir on 06.04.2016.
 */
@RestController
@RequestMapping(value = "/api/tasks")
public class TaskController extends BaseRestController<MeetTask, Long> {

    @Autowired
    public TaskController(TaskService service) {
        super(service);
    }
}
