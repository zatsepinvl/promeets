package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserChat;
import ru.unc6.promeets.model.entity.UserChatPK;

/**
 * Created by Vladimir on 13.04.2016.
 */
public interface UserChatRepository extends CrudRepository<UserChat, UserChatPK> {
    @Query("select userChat.userChatPK.user from UserChat userChat where userChat.userChatPK.chat.chatId=(:chatId)")
    Iterable<User> getUsersByChatId(@Param("chatId") long chatId);
}
