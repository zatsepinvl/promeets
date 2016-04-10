package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserMeet;
import ru.unc6.promeets.model.entity.UserMeetPK;

/**
 * Created by Vladimir on 10.04.2016.
 */

@Transactional
public interface UserMeetRepository extends CrudRepository<UserMeet, UserMeetPK> {
    @Query("select userMeet from UserMeet userMeet where  userMeet.userMeetPK.meet.meetId=(:meetId)")
    Iterable<UserMeet> getUserMeetsByMeetId(@Param("meetId") long id);

    @Query("select userMeet from UserMeet userMeet where userMeet.userMeetPK.user.id=(:userId)")
    Iterable<UserMeet> getUserMeetsByUserId(@Param("userId") long userId);

    @Query("select userMeetPK.user from UserMeet userMeet where  userMeet.userMeetPK.meet.meetId=(:meetId)")
    Iterable<User> getUsersByMeetId(@Param("meetId") long id);

    @Query("select userMeetPK.meet from UserMeet userMeet where  userMeet.userMeetPK.user.userId=(:userId)")
    Iterable<User> getMeetsByUserId(@Param("userId") long id);

    @Query("select userMeet from UserMeet userMeet where userMeet.userMeetPK.user.userId=(:userId) and userMeet.viewed=false")
    Iterable<UserMeet> getNotViewedMeetsByUserId(@Param("userId") long userId);

    @Modifying
    @Query("delete from UserMeet userMeet where userMeet.userMeetPK.meet.meetId=(:meetId)")
    void deleteUserMeetsByMeetId(@Param("meetId") long id);
}
