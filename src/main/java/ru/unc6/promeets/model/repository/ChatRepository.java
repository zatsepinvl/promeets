
package ru.unc6.promeets.model.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.Chat;
import ru.unc6.promeets.model.entity.Message;
import ru.unc6.promeets.model.entity.User;
public interface ChatRepository extends CrudRepository<Chat, Long>
{
    @Query("select message from Message message where message.chat.chatId=(:chatId) order by message.time DESC")
    Page<Message> getMessagesPageByChatId(@Param("chatId") Long id, Pageable pageable);
    
    @Query("select message from Message message where message.chat.chatId=(:chatId) order by message.time DESC")
    Iterable<Message> getAllMessagesByChatId(@Param("chatId") Long id);
}

