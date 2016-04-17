package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserMessage;
import ru.unc6.promeets.model.entity.UserMessagePK;
import ru.unc6.promeets.model.repository.UserChatRepository;
import ru.unc6.promeets.model.repository.UserMessageRepository;
import ru.unc6.promeets.model.service.entity.UserMessageService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladimir on 13.04.2016.
 */
@Service
public class UserMessageServiceImpl extends BaseServiceImpl<UserMessage, UserMessagePK>
        implements UserMessageService {

    private static final int PAGE_SIZE = 10;
    private static final Sort PAGE_SORT = new Sort(Sort.Direction.DESC, "time");
    private UserMessageRepository userMessageRepository;

    @Autowired
    private UserChatRepository userChatRepository;

    @Autowired
    public UserMessageServiceImpl(UserMessageRepository repository) {
        super(repository);
        this.userMessageRepository = repository;
    }

    @Override
    public UserMessage getUserMessageByUserIdAndMessageId(long userId, long messageId) {
        return userMessageRepository.getUserMessageByUserIdAndMessageId(userId, messageId);
    }

    @Override
    public List<UserMessage> getUserMessagesByUserIdAndChatId(long userId, long chatId, int pageId) {
        return userMessageRepository
                .getUserMessagesByUserIdAndChatId(userId, chatId, new PageRequest(pageId, PAGE_SIZE))
                .getContent();
    }



    @Override
    public List<UserMessage> getNewUserMessagesByUserId(long userId) {
        return (List<UserMessage>) userMessageRepository.getNewUserMessagesCountByUserId(userId);
    }

    @Override
    public void deleteUserMessagesByChatId(long chatId) {
        userMessageRepository.deleteUserMessagesByChatId(chatId);
    }

    @Override
    public void createUserMessagesByMessage(Message message) {
        long chatId = message.getChat().getChatId();
        List<UserMessage> userMessages = new ArrayList<>();
        for (User user : userChatRepository.getUsersByChatId(chatId)) {
            UserMessage userMessage = new UserMessage();
            UserMessagePK userMessagePK = new UserMessagePK();
            userMessagePK.setMessage(message);
            userMessagePK.setUser(user);
            userMessage.setUserMessagePK(userMessagePK);
            userMessage.setViewed(user.getUserId() == message.getUser().getUserId());
            userMessages.add(userMessage);
        }
        userMessageRepository.save(userMessages);
    }
}
