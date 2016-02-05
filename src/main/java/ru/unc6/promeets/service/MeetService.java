/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.service;

import java.util.List;
import ru.unc6.promeets.model.entity.Meet;

/**
 *
 * @author MDay
 */
public interface MeetService 
{
    Meet getById(long id);
     
    void addMeet(Meet meet);
     
    void updateMeet(Meet meet);
     
    void deleteMeet(Meet meet);
 
    List<Meet> getAll(); 
  
}
