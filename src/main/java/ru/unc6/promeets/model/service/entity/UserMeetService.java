package ru.unc6.promeets.model.service.entity;

import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.entity.UserMeet;
import ru.unc6.promeets.model.entity.UserMeetPK;

import java.util.List;

/**
 * Created by Vladimir on 10.04.2016.
 */

public interface UserMeetService extends BaseService<UserMeet, UserMeetPK> {

    List<UserMeet> getUserMeetsByMeetId(long id);

    List<UserMeet> getUserMeetsByUserId(long id);

    List<UserMeet> getNotViewedMeetsByUserId(long id);

    void createUserMeetsByMeet(Meet meet);

    void deleteUserMeetsByMeetId(long meetId);


}
