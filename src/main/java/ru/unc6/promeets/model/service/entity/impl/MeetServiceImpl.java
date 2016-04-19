/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.entity.impl;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.*;
import ru.unc6.promeets.model.entity.MeetTask;
import ru.unc6.promeets.model.repository.MeetRepository;
import ru.unc6.promeets.model.service.entity.BoardService;
import ru.unc6.promeets.model.service.entity.MeetService;
import ru.unc6.promeets.model.service.entity.UserMeetService;
import ru.unc6.promeets.model.service.notification.MeetNotificationService;
import ru.unc6.promeets.model.service.notification.impl.BaseNotificationServiceImpl;


@Service
@Transactional
public class MeetServiceImpl extends BaseNotificatedServiceImpl<Meet, Long>
        implements MeetService {

    private static final Logger log = Logger.getLogger(MeetServiceImpl.class);

    private MeetRepository meetRepository;

    private MeetNotificationService notificationService;

    @Autowired
    private UserMeetService userMeetService;

    @Autowired
    private BoardService boardService;


    @Autowired
    public MeetServiceImpl(MeetRepository repository, MeetNotificationService notificationService) {
        super(repository, notificationService);
        this.meetRepository = repository;
        this.notificationService = notificationService;
    }

    @Override
    public Meet getById(Long id) {
        return meetRepository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        Board board = meetRepository.findOne(id).getBoard();
        if (board != null) {
            boardService.delete(board.getBoardId());
        }
        meetRepository.deleteAllNotesById(id);
        meetRepository.deleteAllAimsById(id);
        userMeetService.deleteUserMeetsByMeetId(id);
        super.delete(id);
    }


    @Override
    public Meet create(Meet meet) {
        meet = super.create(meet);
        userMeetService.createUserMeetsByMeet(meet);
        return meet;
    }

    @Override
    public List<MeetNote> getMeetNotes(long id) {
        return (List<MeetNote>) meetRepository.getMeetNotesByMeetId(id);
    }

    @Override
    public List<MeetTask> getMeetAims(long id) {
        return (List<MeetTask>) meetRepository.getMeetTasksByMeetId(id);
    }

    @Override
    public Board getBoard(long id) {
        return getById(id).getBoard();
    }

}
