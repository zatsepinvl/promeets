package ru.unc6.promeets.model.service.entity;

import ru.unc6.promeets.model.entity.*;

import java.util.List;

/**
 * Created by Vladimir on 10.04.2016.
 */

public interface UserMeetService extends BaseService<UserMeet, UserMeetPK> {

    List<UserMeet> getUserMeetsByMeetId(long id);

    List<UserMeet> getUserMeetsByUserId(long id);

    List<UserMeet> getUserMeetsByUserIdAndTime(long id, long start, long end);

    List<UserMeet> getNotViewedMeetsByUserId(long id);

    UserMeet getUserMeetByUserIdAndMeetId(long userId, long meetId);

    List<UserMeet> getUserMeetsByGroupIdAndTimePeriodAndUserId(long groupId, long userId, long start, long end);

    void createUserMeetsByMeet(Meet meet);

    void deleteUserMeetsByMeetId(long meetId);

    void deleteUserMeetByUserIdAndMeetId(long userId, long meetId);

    void deleteUserMeetByGroupId(long groupId);

    void createUserMeetsByUserAndGroup(User user, Group group);

}
