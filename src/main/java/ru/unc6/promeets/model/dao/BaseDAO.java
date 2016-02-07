package ru.unc6.promeets.model.dao;

import java.util.List;

/**
 * Created by Vladimir on 07.02.2016.
 */
public interface BaseDAO<T> {
    T getById(Class<T> entity, long id);

    T create(T obj);

    T update(T obj);

    void delete(Class<T> entity, long id);

    List<T> getAll(Class<T> entity);
}
