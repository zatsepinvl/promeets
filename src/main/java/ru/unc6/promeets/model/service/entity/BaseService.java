
package ru.unc6.promeets.model.service.entity;

import java.util.List;

/**
 * @param <T>
 * @author MDay
 */
public interface BaseService<T> {
    T getById(long id);

    T save(T entity);

    void delete(long id);

    List<T> getAll();
}
