
package ru.unc6.promeets.model.service;

import java.util.List;

/**
 *
 * @author MDay
 * @param <T>
 */
public interface  BaseService<T> 
{
    T getById(long id);

    void save(T entity);

    void delete(long id);

    List<T> getAll();
}
