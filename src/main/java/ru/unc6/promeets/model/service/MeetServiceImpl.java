/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.dao.MeetDAO;
import ru.unc6.promeets.model.entity.Board;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.MeetNote;
import ru.unc6.promeets.model.entity.MeetTarget;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserMeet;
import ru.unc6.promeets.model.repository.MeetRepository;

/**
 *
 * @author MDay
 */

@Service
@Transactional
public class MeetServiceImpl implements MeetService
{
    @Autowired
    private MeetRepository meetRepository;
    @Autowired
    private MeetDAO meetDAO;

    @Override
    public Meet getById(long id) 
    {
      return  meetRepository.findOne(id);
    }


    @Override
    public void delete(long id) 
    {
        meetRepository.delete(id);
    }

    @Override
    public List<Meet> getAll() 
    {
       return  (List)meetRepository.findAll();
    }
    
    @Override
    public List<User>getUsers (long id)
    {
       List<User> users;
       
        users = (List) meetRepository.getAllUsersByMeetId(id);
       
       return users;
    }

    @Override
    public void save(Meet meet) 
    {
            meetRepository.save(meet);
    }

    @Override
    public List<UserMeet> getUserMeets(long id) 
    {
        List<UserMeet> userMeets;
       
        userMeets = (List) meetRepository.getAllUserMeetsByMeetId(id);
       
       return userMeets;
    }

    @Override

    public List<MeetNote> getMeetNotes(long id) 
    {
        List<MeetNote> meetNotes = (List) meetRepository.getAllMeetNotesByMeetId(id);

        
        return meetNotes; 
    }

    @Override
    public List<MeetTarget> getMeetTargets(long id) 
    {
        List<MeetTarget> meetTargets = (List) meetRepository.getAllMeetTargetsByMeetId(id);
        
        return meetTargets;
    }

    @Override
    public Board getBoard(long id) 
    {
       Meet meet = getById(id);
       
       return meet.getBoard();
    }
  
}
