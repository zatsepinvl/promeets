package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.model.entity.*;
import ru.unc6.promeets.model.repository.UserMessageRepository;
import ru.unc6.promeets.model.service.entity.MessageService;
import ru.unc6.promeets.model.service.entity.UserMessageService;
import ru.unc6.promeets.model.service.entity.UserService;
import ru.unc6.promeets.model.service.notification.impl.UserMessageNotificationService;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Vladimir on 13.04.2016.
 */
@Service
public class UserMessageServiceImpl extends BaseNotifiedServiceImpl<UserMessage, UserMessagePK>
        implements UserMessageService {

    @Value("${messages-page-size}")
    private int PAGE_SIZE;

    private static final long TIME_OFFSET = 7776000000L; //90 days
    private static final Sort PAGE_SORT = new Sort(Sort.Direction.DESC, "time");
    private static final PageRequest LAST_MESSAGE_PAGE_REQUEST = new PageRequest(0, 1);
    private UserMessageRepository userMessageRepository;


    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMessageNotificationService notificationService;

    @Autowired
    public UserMessageServiceImpl(UserMessageRepository repository, UserMessageNotificationService notificationService) {
        super(repository, notificationService);
        this.userMessageRepository = repository;
        this.notificationService = notificationService;
    }

    @Override
    public UserMessage create(UserMessage entity) {
        if (messageService.getById(entity.getMessage().getMessageId()) == null) {
            messageService.create(entity.getMessage());
        }
        entity = userMessageRepository.save(entity);
        onMessageCreated(entity);
        return entity;
    }

    @Async
    protected void onMessageCreated(UserMessage entity) {
        Message message = entity.getMessage();
        long chatId = message.getChat().getChatId();
        for (User user : userService.getUsersByChatId(chatId)) {
            UserMessage userMessage = new UserMessage();
            userMessage.setUser(user);
            userMessage.setMessage(entity.getMessage());
            userMessage.setViewed(user.getUserId() == message.getUser().getUserId());
            if (message.getUser().getUserId() != user.getUserId()) {
                super.create(userMessage);
            }
        }
    }

    @Override
    public UserMessage update(UserMessage entity) {
        entity = userMessageRepository.save(entity);
        onMessageUpdated(entity);
        return entity;
    }

    @Async
    protected void onMessageUpdated(UserMessage entity) {
        if (entity.isViewed() && entity.getUser().getUserId() != entity.getMessage().getUser().getUserId()) {
            UserMessage userMessage = getUserMessageByUserIdAndMessageId(entity.getMessage().getUser().getUserId(), entity.getMessage().getMessageId());
            if (!userMessage.isViewed()) {
                userMessage.setViewed(true);
                userMessageRepository.save(userMessage);
                notificationService.onUpdate(userMessage);
            }
        }
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
        return (List<UserMessage>) userMessageRepository.getNewUserMessagesByUserId(userId);
    }

    @Override
    public List<UserMessage> getNewUserMessagesByUserIdAndChatId(long userId, long chatId) {
        return (List<UserMessage>) userMessageRepository.getNewUserMessagesByUserIdAndChatId(userId, chatId);
    }

    @Override
    public void deleteUserMessagesByChatId(long chatId) {
        userMessageRepository.deleteUserMessagesByChatId(chatId);
    }

    @Override
    public UserMessage getLastUserMessageByUserIdAndChatId(long userId, long chatId) {
        Page<UserMessage> messages = userMessageRepository.getLastMessageByUserIdAndChatId(userId, chatId, LAST_MESSAGE_PAGE_REQUEST);
        return messages.getTotalElements() > 0 ? messages.getContent().get(0) : null;
    }

    @Override
    public void createUserMessagesByUserAndChat(User user, Chat chat) {
        for (Message message : messageService.getMessagesByChatIdAfter(chat.getChatId(), System.currentTimeMillis() - TIME_OFFSET)) {
            UserMessage userMessage = new UserMessage();
            userMessage.setUser(user);
            userMessage.setViewed(true);
            userMessage.setMessage(message);
            onMessageUpdated(userMessageRepository.save(userMessage));
        }
    }


}
