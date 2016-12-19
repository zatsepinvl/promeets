package com.promeets.model.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.promeets.model.entity.User;
import com.promeets.model.entity.UserMeet;
import com.promeets.model.entity.UserMeetPK;

/**
 * Created by Vladimir on 10.04.2016.
 */


public interface UserMeetRepository extends CrudRepository<UserMeet, UserMeetPK> {
    @Query("select userMeet from UserMeet userMeet where  userMeet.userMeetPK.meet.meetId=(:meetId) order by userMeet.id.user.userId")
    Iterable<UserMeet> getUserMeetsByMeetId(@Param("meetId") long id);

    @Query("select userMeet from UserMeet userMeet where userMeet.userMeetPK.user.id=(:userId)")
    Iterable<UserMeet> getUserMeetsByUserId(@Param("userId") long userId);

    @Query("select userMeetPK.user from UserMeet userMeet where  userMeet.userMeetPK.meet.meetId=(:meetId)")
    Iterable<User> getUsersByMeetId(@Param("meetId") long id);

    @Query("select userMeetPK.meet from UserMeet userMeet where  userMeet.userMeetPK.user.userId=(:userId)")
    Iterable<User> getMeetsByUserId(@Param("userId") long id);

    @Query("select userMeet from UserMeet userMeet where userMeet.userMeetPK.user.userId=(:userId) and userMeet.viewed=false")
    Iterable<UserMeet> getNotViewedMeetsByUserId(@Param("userId") long userId);

    @Query("select userMeet from UserMeet userMeet where userMeet.id.user.userId=(:userId) and userMeet.id.meet.meetId = (:meetId)")
    UserMeet getUserMeetByUserIdAndMeetId(@Param("userId") long userId, @Param("meetId") long meetId);

    @Query("select userMeet from UserMeet userMeet where  userMeet.id.user.userId=(:userId) and userMeet.id.meet.group.groupId=(:groupId) and userMeet.id.meet.time>=(:start) and userMeet.id.meet.time<=(:end) order by userMeet.id.meet.time")
    Iterable<UserMeet> getUserMeetsByGroupIdAndUserIdAndTimePeriod(@Param("groupId") long groupId, @Param("userId") long userId, @Param("start") long start, @Param("end") long end);

    @Query("select userMeet from UserMeet userMeet where  userMeet.id.user.userId=(:userId) and userMeet.id.meet.time>=(:start) and userMeet.id.meet.time<=(:end) order by userMeet.id.meet.time")
    Iterable<UserMeet> getUserMeetsByUserIdAndTimePeriod(@Param("userId") long userId, @Param("start") long start, @Param("end") long end);

    @Modifying
    @Transactional
    @Query("delete from UserMeet userMeet where userMeet.userMeetPK.meet.meetId=(:meetId)")
    void deleteUserMeetsByMeetId(@Param("meetId") long id);

    @Modifying
    @Transactional
    @Query("delete from UserMeet userMeet where userMeet.id.user.userId=(:userId) and userMeet.id.meet.meetId=(:meetId)")
    void deleteUserMeetByUserIdAndMeetId(@Param("userId") long userId, @Param("meetId") long meetId);

    @Modifying
    @Transactional
    @Query("delete from UserMeet userMeet where userMeet.id.meet.group.groupId=(:groupId)")
    void deleteUserMeetsByGroupId(@Param("groupId") long groupId);
}
