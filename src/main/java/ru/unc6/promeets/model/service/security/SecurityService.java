package ru.unc6.promeets.model.service.security;

import ru.unc6.promeets.model.entity.User;

import java.io.Serializable;

/**
 * Created by Vladimir on 08.05.2016.
 */
public interface SecurityService<T, V extends Serializable> {
    boolean checkIsUpdateAllow(T entity, User user);

    boolean checkIsCreateAllow(T entity, User user);

    boolean checkIsDelteAllow(T entity, User user);

    boolean checkIsReadAllow(T entity, User user);
}
