/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service;

import java.util.List;

import ru.unc6.promeets.model.entity.*;
import ru.unc6.promeets.model.entity.MeetAim;

/**
 *
 * @author MDay
 */
public interface MeetService extends BaseService<Meet>
{
    List<User> getUsers(long id);
    
    List<UserMeet> getUserMeets(long id);
    
    List<MeetNote> getMeetNotes(long id);
    
    List<MeetAim> getMeetAims(long id);
    
    Board getBoard(long id); 
}
