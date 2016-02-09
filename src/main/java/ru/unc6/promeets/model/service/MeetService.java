/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service;

import java.util.List;
import ru.unc6.promeets.model.entity.Board;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.MeetNote;
import ru.unc6.promeets.model.entity.MeetTarget;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserMeet;

/**
 *
 * @author MDay
 */
public interface MeetService 
{
    Meet getById(long id);

    void save(Meet meet);

    void delete(long id);

    List<Meet> getAll();
    
    List<User> getUsers(long id);
    
    List<UserMeet> getUserMeets(long id);
    
    List<MeetNote> getMeetNotes(long id);
    
    List<MeetTarget> getMeetTargets(long id);
    
    Board getBoard(long id);
}
