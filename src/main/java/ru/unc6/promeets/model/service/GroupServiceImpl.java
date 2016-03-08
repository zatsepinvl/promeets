/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service;

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
    public void save(Group group) {
        groupRepository.save(group);

        log.debug("Save group with id=" + group.getGroupId());
    }

    @Override
    public void delete(long id) {
        groupRepository.deleteAllUserGroupssByGroupId(id);
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
    public List<GroupType> getGroupTypes() {
        return (List<GroupType>) groupRepository.getAllGroupTypes();
    }

}
