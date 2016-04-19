package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.data.repository.CrudRepository;
import ru.unc6.promeets.model.service.entity.BaseService;
import ru.unc6.promeets.model.service.notification.BaseNotificationService;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vladimir on 19.04.2016.
 */
public class BaseNotificatedServiceImpl<T, V extends Serializable> implements BaseService<T, V> {

    private CrudRepository<T, V> repository;
    private BaseNotificationService<T> notificationService;

    public BaseNotificatedServiceImpl(CrudRepository<T, V> repository, BaseNotificationService<T> notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    @Override
    public T getById(V id) {
        return repository.findOne(id);
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

    @Override
    public List<T> getAll() {
        return (List<T>) repository.findAll();
    }
}
