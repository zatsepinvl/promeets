/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.Group;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserGroup;

/**
 *
 * @author MDay
 */
public interface GroupRepository extends CrudRepository<Group, Long>
{
    @Query("select userGroupPK.user from UserGroup userGroup where  userGroup.userGroupPK.group.groupId=(:groupId)")
    Iterable<User> getAllUsersByGroupId(@Param("groupId") Long id);
    
    @Query("select userGroup from UserGroup userGroup where  userGroup.userGroupPK.group.groupId=(:groupId)")
    Iterable<UserGroup> getAllUserGroupsByGroupId(@Param("groupId") Long id);
    
    @Query("select meet from Meet meet where  meet.group.groupId=(:groupId)")
    Iterable<Meet> getAllMeetsByGroupId(@Param("groupId") Long id);
    
    @Modifying
    @Query("delete from UserGroup userGroup where userGroup.userGroupPK.group.groupId=(:groupId)")
    void deleteAllUserGroupssByGroupId(@Param("groupId") Long id);
    
}
