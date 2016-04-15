package ru.unc6.promeets.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.UserMessage;
import ru.unc6.promeets.model.entity.UserMessagePK;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Vladimir on 12.04.2016.
 */
@Transactional
public interface UserMessageRepository extends PagingAndSortingRepository<UserMessage, UserMessagePK> {

    @Query("select userMessage from UserMessage  userMessage where userMessage.id.user.id=(:userId) and userMessage.id.message.id=(:messageId)")
    UserMessage getUserMessageByUserIdAndMessageId(@Param("userId") long userId, @Param("messageId") long messageId);

    @Query("select userMessage from UserMessage userMessage where userMessage.id.user.userId=(:userId) and userMessage.id.message.chat.chatId=(:chatId) order by userMessage.id.message.time desc")
    Page<UserMessage> getUserMessagesByUserIdAndChatId(@Param("userId") long userId, @Param("chatId") long chatId, Pageable pageable);

    @Query("select count(userMessage) from UserMessage userMessage where userMessage.id.user.userId=(:userId) and userMessage.id.message.chat.chatId=(:chatId) and userMessage.viewed=false group by userMessage.id.message.id")
    int getNewUserMessagesCountByUserIdAndChatId(@Param("userId") long userId, @Param("chatId") long chatId);

    @Query("select count(userMessage) from UserMessage userMessage where userMessage.id.user.userId=(:userId) and userMessage.viewed=false group by userMessage.id.message.id")
    int getNewUserMessagesCountByUserId(@Param("userId") long userId);


    @Modifying
    @Query("delete from UserMessage userMessage where userMessage.id.message.chat.chatId=(:chatId)")
    void deleteUserMessagesByChatId(@Param("chatId") long chatId);
}
