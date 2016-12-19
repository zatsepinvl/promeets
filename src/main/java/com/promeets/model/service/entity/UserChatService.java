package com.promeets.model.service.entity;

import com.promeets.model.entity.Chat;
import com.promeets.model.entity.User;
import com.promeets.model.entity.UserChat;
import com.promeets.model.entity.UserChatPK;

import java.util.List;

/**
 * Created by Vladimir on 13.04.2016.
 */
public interface UserChatService extends BaseService<UserChat, UserChatPK> {
    List<User> getUsersByChatId(long id);

    List<UserChat> getUserChatsByUserId(long userId);

    UserChat getUserChatByUserIdAndChatId(long userId, long chatId);

    void createUserChatByUserAndChat(User user, Chat chat);

    void deleteUserChatByChatId(long id);
}
