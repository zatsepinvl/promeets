/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.*;

import java.sql.Timestamp;

/**
 * @author MDay
 */
public interface GroupRepository extends CrudRepository<Group, Long> {
    @Query("select userGroupPK.user from UserGroup userGroup where  userGroup.userGroupPK.group.groupId=(:groupId)")
    Iterable<User> getAllUsersByGroupId(@Param("groupId") Long id);

    @Query("select userGroup from UserGroup userGroup where  userGroup.userGroupPK.group.groupId=(:groupId)")
    Iterable<UserGroup> getAllUserGroupsByGroupId(@Param("groupId") Long id);

    @Cacheable(value = "groupMeetsById")
    @Query("select meet from Meet meet where  meet.group.groupId=(:groupId) order by meet.time")
    Iterable<Meet> getAllMeetsByGroupId(@Param("groupId") Long id);

    @Modifying
    @Query("delete from UserGroup userGroup where userGroup.userGroupPK.group.groupId=(:groupId)")
    void deleteAllUserGroupsByGroupId(@Param("groupId") Long id);

    @Query("select groupType from GroupType groupType")
    Iterable<GroupType> getAllGroupTypes();

}
