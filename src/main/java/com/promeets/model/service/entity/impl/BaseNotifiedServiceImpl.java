package com.promeets.model.service.entity.impl;

import com.promeets.model.service.notification.BaseNotificationService;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

/**
 * Created by Vladimir on 19.04.2016.
 */
public class BaseNotifiedServiceImpl<T, V extends Serializable> extends BaseServiceImpl<T, V> {

    private CrudRepository<T, V> repository;
    private BaseNotificationService<T> notificationService;

    public BaseNotifiedServiceImpl(CrudRepository<T, V> repository, BaseNotificationService<T> notificationService) {
        super(repository);
        this.repository = repository;
        this.notificationService = notificationService;
    }

    @Override
    public T create(T entity) {
        entity = repository.save(entity);
        notificationService.onCreate(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        entity = repository.save(entity);
        notificationService.onUpdate(entity);
        return entity;
    }

    @Override
    public void delete(V id) {
        T entity = repository.findOne(id);
        repository.delete(id);
        notificationService.onDelete(entity);
    }
}
