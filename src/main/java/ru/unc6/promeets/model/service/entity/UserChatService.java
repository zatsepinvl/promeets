package ru.unc6.promeets.model.service.entity;

import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserChat;
import ru.unc6.promeets.model.entity.UserChatPK;

import java.util.List;

/**
 * Created by Vladimir on 13.04.2016.
 */
public interface UserChatService extends BaseService<UserChat, UserChatPK> {
    List<User> getUsersByChatId(long id);
}
