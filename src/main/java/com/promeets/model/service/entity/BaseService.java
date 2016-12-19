
package com.promeets.model.service.entity;

import java.util.List;

public interface BaseService<T, V> {
    T getById(V id);

    T create(T entity);

    T update(T entity);

    void delete(V id);

    List<T> getAll();
}
