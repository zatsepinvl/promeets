/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.service.entity.impl;

import com.promeets.model.service.entity.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promeets.model.entity.Meet;
import com.promeets.model.repository.MeetRepository;
import com.promeets.model.service.notification.MeetNotificationService;

import java.util.List;


@Service
public class MeetServiceImpl extends BaseNotifiedServiceImpl<Meet, Long>
        implements MeetService {

    private static final Logger log = Logger.getLogger(MeetServiceImpl.class);

    private MeetRepository meetRepository;

    @Autowired
    private UserMeetService userMeetService;

    @Autowired
    private CardService cardService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private TaskService taskService;

    @Autowired
    public MeetServiceImpl(MeetRepository repository, MeetNotificationService notificationService) {
        super(repository, notificationService);
        this.meetRepository = repository;
    }

    @Override
    public void delete(Long id) {
        userMeetService.deleteUserMeetsByMeetId(id);
        cardService.deleteCardByMeetId(id);
        noteService.deleteNotesByMeetId(id);
        taskService.deleteTasksByMeetId(id);
        super.delete(id);
    }

    @Override
    public List<Meet> getMeetsByGroupId(long groupId) {
        return (List<Meet>) meetRepository.getMeetsByGroupId(groupId);
    }

    @Override
    public void deleteMeetsByGroupId(long groupId) {
        userMeetService.deleteUserMeetByGroupId(groupId);
        meetRepository.deleteMeetsByGroupId(groupId);
    }
}
