/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.Board;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.MeetNote;
import ru.unc6.promeets.model.entity.MeetAim;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserMeet;
import ru.unc6.promeets.model.repository.MeetRepository;

/**
 * @author MDay
 */

@Service
@Transactional
public class MeetServiceImpl implements MeetService {
    @Autowired
    private MeetRepository meetRepository;

    @Override
    public Meet getById(long id) {
        return meetRepository.findOne(id);
    }

    @Override
    public void delete(long id) {
        meetRepository.deleteAllAimsById(id);
        meetRepository.delete(id);
    }

    @Override
    public List<Meet> getAll() {
        return (List<Meet>) meetRepository.findAll();
    }

    @Override
    public List<User> getUsers(long id) {
        return (List<User>) meetRepository.getAllUsersByMeetId(id);
    }

    @Override
    public void save(Meet meet) {
        meetRepository.save(meet);
    }

    @Override
    public List<UserMeet> getUserMeets(long id) {
        return (List<UserMeet>) meetRepository.getAllUserMeetsByMeetId(id);
    }

    @Override
    public List<MeetNote> getMeetNotes(long id) {
        return (List<MeetNote>) meetRepository.getAllMeetNotesByMeetId(id);
    }

    @Override
    public List<MeetAim> getMeetAims(long id) {
        return (List<MeetAim>) meetRepository.getAllMeetAimsByMeetId(id);
    }

    @Override
    public Board getBoard(long id) {
        return getById(id).getBoard();
    }

}
