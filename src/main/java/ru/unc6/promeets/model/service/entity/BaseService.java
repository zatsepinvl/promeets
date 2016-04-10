
package ru.unc6.promeets.model.service.entity;

import java.util.List;

public interface BaseService<T,V> {
    T getById(V id);

    T save(T entity);

    void delete(V id);

    List<T> getAll();
}
