package ru.unc6.promeets.model.service.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Vladimir on 04.04.2016.
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    private CrudRepository<T, Long> repository;

    public BaseServiceImpl(CrudRepository<T, Long> repository) {
        this.repository = repository;
    }


    @Override
    public T getById(long id) {
        return repository.findOne(id);
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }

    @Override
    public List<T> getAll() {
        return (List<T>) repository.findAll();
    }
}
