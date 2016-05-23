package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.User;

/**
 * Created by Vladimir on 28.02.2016.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "select user from User user where user.email=(:email)")
    User getUserByEmail(@Param("email") String email);

    @Query("select userChat.userChatPK.user from UserChat userChat where  userChat.userChatPK.chat.chatId=(:chatId) order by userChat.id.user.userId")
    Iterable<User> getUsersByChatId(@Param("chatId") Long id);

    @Query(value = "select user from User user where " +
            "lower(user.firstName) like concat(:firstName,'%') " +
            "and lower(user.lastName) like concat(:lastName,'%') order by user.firstName, user.lastName")
    Iterable<User> findByFirstNameAndLastName(@Param("firstName") String fistName, @Param("lastName") String lastName);

    User findByEmailOrderByEmail(String email);
}
