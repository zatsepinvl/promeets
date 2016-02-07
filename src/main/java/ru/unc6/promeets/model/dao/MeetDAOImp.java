package ru.unc6.promeets.model.dao;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.*;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Vladimir on 07.02.2016.
 */
@Repository
@Transactional
public class MeetDAOImp extends BaseDAOImp<Meet> implements MeetDAO {

    @Override
    @Transactional
    public List<UserMeet> getAllUsersByMeetId(long id) {
        Meet meet = getById(Meet.class, id);
        Hibernate.initialize(meet.getUsers());
        return meet.getUsers();
    }

    @Override
    public List<MeetNote> getAllNotesByMeetId(long id) {
        return null;
    }

    @Override
    public List<MeetTarget> getAllTargetsByMeetId(long id) {
        return null;
    }
}
