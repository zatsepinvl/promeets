/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.*;
import ru.unc6.promeets.model.repository.GroupRepository;

/**
 * @author MDay
 */
@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private static final long day = 1000 * 60 * 60 * 24;
    private static final long month = day * 30;
    private static final Logger log = Logger.getLogger(MeetServiceImpl.class);

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    MeetService meetService;

    @Override
    public Group getById(long id) {
        return groupRepository.findOne(id);
    }

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void delete(long id) {
        groupRepository.deleteAllUserGroupsByGroupId(id);
        List<Meet> meets = (List<Meet>) groupRepository.getAllMeetsByGroupId(id);
        for (Meet meet : meets) {
            meetService.delete(meet.getMeetId());
        }

        groupRepository.delete(id);
        log.debug("Delete group with id=" + id);
    }

    @Override
    public List<Group> getAll() {
        return (List<Group>) groupRepository.findAll();
    }

    @Override
    public List<User> getUsersByGroupId(long id) {
        return (List<User>) groupRepository.getAllUsersByGroupId(id);
    }

    @Override
    public List<UserGroup> getUserGroupsByGroupId(long id) {
        return (List<UserGroup>) groupRepository.getAllUserGroupsByGroupId(id);
    }

    @Override
    public List<Meet> getMeetsByGroupId(long id) {
        return (List<Meet>) groupRepository.getAllMeetsByGroupId(id);
    }

    @Override
    public List<Meet> getMeetsByGroupIdAndDay(long id, long date) {
        List<Meet> meets = getMeetsByGroupId(id);
        List<Meet> temp = new ArrayList<>();
        Calendar meetTime = Calendar.getInstance();
        Calendar monthTime = Calendar.getInstance();
        monthTime.setTimeInMillis(date);
        for (Meet meet : meets) {
            meetTime.setTimeInMillis(meet.getTime());
            if (meetTime.get(Calendar.DAY_OF_MONTH) == monthTime.get(Calendar.DAY_OF_MONTH)) {
                temp.add(meet);
            }
        }
        return temp;
    }

    @Override
    public List<Meet> getMeetsByGroupIdAndMonth(long id, long date) {
        List<Meet> meets = getMeetsByGroupId(id);
        List<Meet> temp = new ArrayList<>();
        Calendar meetTime = Calendar.getInstance();
        Calendar monthTime = Calendar.getInstance();
        monthTime.setTimeInMillis(date);
        for (Meet meet : meets) {
            meetTime.setTimeInMillis(meet.getTime());
            if (meetTime.get(Calendar.MONTH) == monthTime.get(Calendar.MONTH)) {
                temp.add(meet);
            }
        }
        return temp;
    }

    @Override
    public List<GroupType> getGroupTypes() {
        return (List<GroupType>) groupRepository.getAllGroupTypes();
    }

}
