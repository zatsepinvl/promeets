package ru.unc6.promeets.model.service;

import java.util.List;

/**
 * Created by Vladimir on 18.02.2016.
 */
public interface BaseService<T> {
    T getById(long id);

    void save(T meet);

    void delete(long id);

    List<T> getAll();
}
