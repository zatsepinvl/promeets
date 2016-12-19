package com.promeets.model.repository;

import com.promeets.model.entity.UserChatPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.promeets.model.entity.User;
import com.promeets.model.entity.UserChat;

/**
 * Created by Vladimir on 13.04.2016.
 */
public interface UserChatRepository extends CrudRepository<UserChat, UserChatPK> {
    @Query("select userChat.userChatPK.user from UserChat userChat where userChat.userChatPK.chat.chatId=(:chatId)")
    Iterable<User> getUsersByChatId(@Param("chatId") long chatId);

    @Query("select userChat from UserChat userChat where userChat.id.user.userId=(:userId)")
    Iterable<UserChat> getUserChatsByUserId(@Param("userId") long userId);

    @Query("select userChat from UserChat userChat where userChat.id.chat.chatId=(:chatId)")
    Iterable<UserChat> getUserChatsByChatId(@Param("chatId") long chatId);

    @Query("select userChat from UserChat userChat where userChat.id.chat.chatId=(:chatId) and userChat.id.user.userId=(:userId)")
    UserChat getUserChatByUserIdAndChatId(@Param("userId") long userId, @Param("chatId") long chatId);

    @Transactional
    @Modifying
    @Query("delete from UserChat userChat where userChat.id.chat.chatId=(:chatId)")
    void deleteUserChatsByChatId(@Param("chatId") long chatId);
}
