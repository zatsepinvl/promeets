package com.promeets.controller.rest;

import com.promeets.model.service.entity.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

public class BaseRestController<T, V extends Serializable>
        extends BaseController<T, V> {

    private BaseService<T, V> service;

    public BaseRestController(BaseService<T, V> service) {
        super(service);
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public T getById(@PathVariable("id") V id) {
        return getAndCheckIsNotFoundById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public T create(@RequestBody T entity) {
        entity = service.create(entity);
        return entity;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public T updateById(@PathVariable("id") V id, @RequestBody T entity) {
        checkIsNotFoundById(id);
        return service.update(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") V id) {
        checkIsNotFoundById(id);
        service.delete(id);
    }
}
