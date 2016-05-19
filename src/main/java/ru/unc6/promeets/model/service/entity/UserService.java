package ru.unc6.promeets.model.service.entity;

import ru.unc6.promeets.model.entity.User;

import java.util.List;

/**
 * Created by Vladimir on 20.03.2016.
 */
public interface UserService extends BaseService<User, Long> {
    User getUserByEmail(String email);

    List<User> getUsersByChatId(long chatId);

    User getCurrentAuthenticatedUser();

    void updateCurrentAuthenticatedUser(User currentUser);

    List<User> searchByFistNameAndLastName(String firstName, String lastName);

    List<User> searchByEmail(String email);
}
