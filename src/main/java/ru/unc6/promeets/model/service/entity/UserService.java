package ru.unc6.promeets.model.service.entity;

import ru.unc6.promeets.model.entity.User;

/**
 * Created by Vladimir on 20.03.2016.
 */
public interface UserService extends BaseService<User> {
    User getUserByEmail(String email);
}
