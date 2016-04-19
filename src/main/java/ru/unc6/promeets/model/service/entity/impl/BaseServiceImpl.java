package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.data.repository.CrudRepository;
import ru.unc6.promeets.model.service.entity.BaseService;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<T, V extends Serializable> implements BaseService<T, V> {


    private CrudRepository<T, V> repository;

    public BaseServiceImpl(CrudRepository<T, V> repository) {
        this.repository = repository;
    }


    @Override
    public T getById(V id) {
        return repository.findOne(id);
    }

    @Override
    public T create(T entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(V id) {
        repository.delete(id);
    }

    @Override
    public T update(T entity) {
        return repository.save(entity);
    }

    @Override
    public List<T> getAll() {
        return (List<T>) repository.findAll();
    }
}
