package ru.unc6.promeets.model.service.security;

import ru.unc6.promeets.model.entity.User;

import java.io.Serializable;

/**
 * Created by Vladimir on 08.05.2016.
 */
public interface SecurityService<T, V extends Serializable> {
    boolean checkIsUpdatePermit(T entity, User user);

    boolean checkIsUpdatePermit(V id, User user);

    boolean checkIsCreatePermit(T entity, User user);

    boolean checkIsCreatePermit(V id, User user);

    boolean checkIsDeltePermit(T entity, User user);

    boolean checkIsDeltePermit(V id, User user);

    boolean checkIsReadPermit(T entity, User user);

    boolean checkIsReadPermit(V id, User user);
}
