package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.*;
import ru.unc6.promeets.model.repository.UserChatRepository;
import ru.unc6.promeets.model.service.entity.GroupService;
import ru.unc6.promeets.model.service.entity.MessageService;
import ru.unc6.promeets.model.service.entity.UserChatService;
import ru.unc6.promeets.model.service.entity.UserMessageService;
import ru.unc6.promeets.model.service.notification.UserChatNotificationService;

import java.util.List;

/**
 * Created by Vladimir on 13.04.2016.
 */
@Service
public class UserChatServiceImpl extends BaseServiceImpl<UserChat, UserChatPK>
        implements UserChatService {

    private UserChatRepository userChatRepository;

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private GroupService groupService;

    @Autowired
    public UserChatServiceImpl(UserChatRepository repository) {
        super(repository);
        this.userChatRepository = repository;
    }

    @Override
    public List<User> getUsersByChatId(long id) {
        return (List<User>) userChatRepository.getUsersByChatId(id);
    }

    @Override
    public List<UserChat> getUserChatsByUserId(long userId) {
        List<UserChat> chats = (List<UserChat>) userChatRepository.getUserChatsByUserId(userId);
        for (UserChat userChat : chats) {
            List<UserMessage> messages = userMessageService.getNewUserMessagesByUserIdAndChatId(userId, userChat.getChat().getChatId());
            userChat.setLastUserMessage(userMessageService.getLastUserMessageByUserIdAndChatId(userId, userChat.getChat().getChatId()));
            userChat.setNewMessagesCount(messages.size());
            userChat.getChat().setGroup(groupService.getGroupByChatId(userChat.getChat().getChatId()));
        }
        return chats;
    }


    @Override
    public UserChat getUserChatByUserIdAndChatId(long userId, long chatId) {
        return userChatRepository.getUserChatByUserIdAndChatId(userId, chatId);
    }

    @Override
    public void createUserChatByUserAndChat(User user, Chat chat) {
        UserChat userChat = new UserChat();
        userChat.setUser(user);
        userChat.setChat(chat);
        userChatRepository.save(userChat);
    }

    @Override
    public void deleteUserChatByChatId(long id) {
        userChatRepository.getUsersByChatId(id);
    }
}
