package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserChat;
import ru.unc6.promeets.model.entity.UserChatPK;
import ru.unc6.promeets.model.repository.UserChatRepository;
import ru.unc6.promeets.model.service.entity.UserChatService;

import java.util.List;

/**
 * Created by Vladimir on 13.04.2016.
 */
@Service
public class UserChatServiceImpl extends BaseServiceImpl<UserChat, UserChatPK>
        implements UserChatService {

    private UserChatRepository userChatRepository;

    @Autowired
    public UserChatServiceImpl(UserChatRepository repository) {
        super(repository);
        this.userChatRepository = repository;
    }

    @Override
    public List<User> getUsersByChatId(long id) {
        return (List<User>) userChatRepository.getUsersByChatId(id);
    }
}
