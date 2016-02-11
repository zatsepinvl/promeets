/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.dao;

import ru.unc6.promeets.model.entity.*;

import java.util.List;

/**
 * @author MDay
 */
public interface MeetDAO extends BaseDAO<Meet> {

    List<UserMeet> getAllUsersByMeetId(long id);

    List<MeetNote> getAllNotesByMeetId(long id);

    List<MeetAim> getAllTargetsByMeetId(long id);
}
