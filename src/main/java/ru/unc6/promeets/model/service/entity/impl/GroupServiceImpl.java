/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.entity.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.*;
import ru.unc6.promeets.model.repository.GroupRepository;
import ru.unc6.promeets.model.service.entity.*;

@Service
@Transactional
public class GroupServiceImpl extends BaseServiceImpl<Group, Long>
        implements GroupService {

    @Value("${default-group-image-original}")
    private String defaultGroupImageOriginal;
    @Value("${default-group-image-large}")
    private String defaultGroupImageLarge;
    @Value("${default-group-image-medium}")
    private String defaultGroupImageMedium;
    @Value("${default-group-image-small}")
    private String defaultGroupImageSmall;
    @Value("${default-group-image-name}")
    private String defaultGroupImageName;

    private static final long day = 1000 * 60 * 60 * 24;
    private static final long month = day * 30;
    private static final Logger log = Logger.getLogger(MeetServiceImpl.class);


    private GroupRepository groupRepository;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private MeetService meetService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private FileService fileService;


    @Autowired
    public GroupServiceImpl(GroupRepository repository) {
        super(repository);
        this.groupRepository = repository;
    }


    @Override
    public Group create(Group group) {
        group.setChat(chatService.create(new Chat()));
        group.setTime(System.currentTimeMillis());
        group.setAdmin(userService.getCurrentAuthenticatedUser());
        group.setImage(getDefaultImage());
        return groupRepository.save(group);
    }

    private File getDefaultImage() {
        File file = new File();
        file.setOriginal(defaultGroupImageOriginal);
        file.setLarge(defaultGroupImageLarge);
        file.setMedium(defaultGroupImageMedium);
        file.setSmall(defaultGroupImageSmall);
        file.setName(defaultGroupImageName);
        file.setTime(System.currentTimeMillis());
        return fileService.create(file);
    }

    @Override
    public void delete(Long id) {
        userGroupService.deleteUserGroupsByGroupId(id);
        List<Meet> meets = meetService.getMeetsByGroupId(id);
        for (Meet meet : meets) {
            meetService.delete(meet.getMeetId());
        }
        groupRepository.delete(id);
    }

    @Override
    public List<Group> getAll() {
        return (List<Group>) groupRepository.findAll();
    }

    @Override
    public List<User> getUsersByGroupId(long id) {
        return userGroupService.getUsersByGroupId(id);
    }

    @Override
    public List<UserGroup> getUserGroupsByGroupId(long id) {
        return userGroupService.getUserGroupsByGroupId(id);
    }

    @Override
    public List<Meet> getMeetsByGroupId(long id) {
        return meetService.getMeetsByGroupId(id);
    }


    @Override
    public List<Meet> getMeetsByGroupIdAndTimePeriod(long id, long start, long end) {
        return (List<Meet>) groupRepository.getMeetsByGroupIdAndTimePeriod(id, start, end);
    }

    @Override
    public List<GroupType> getGroupTypes() {
        return (List<GroupType>) groupRepository.getGroupTypes();
    }

    @Override
    public Group getGroupByChatId(long chatId) {
        return groupRepository.getGroupByChatId(chatId);
    }


    @Override
    public Group update(Group entity) {
        Chat chat = entity.getChat();
        chat.setTitle(entity.getTitle());
        chatService.update(chat);
        return super.update(entity);
    }
}
