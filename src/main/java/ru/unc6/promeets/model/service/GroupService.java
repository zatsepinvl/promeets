/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service;

import java.util.List;
import ru.unc6.promeets.model.entity.Group;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserGroup;

/**
 *
 * @author MDay
 */
public interface GroupService extends BaseService<Group>
{
    List<User> getUsersByGroupId(long id);
    
    List<UserGroup> getUserGroupsByGroupId(long id);
    
    List<Meet> getMeetsByGroupId(long id);
    
}
