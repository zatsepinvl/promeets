package com.promeets.controller.rest;

import com.promeets.model.service.entity.BaseService;
import com.promeets.controller.exception.NotFoundException;
import com.promeets.controller.exception.NotFoundResponseErrorMessage;
import com.promeets.model.entity.User;

import java.io.Serializable;

/**
 * Created by Vladimir on 12.04.2016.
 */
public abstract class BaseUserRestController<T, V extends Serializable, E> {

    private BaseService<E, Long> entityService;
    private BaseService<T, V> userEntityService;

    public BaseUserRestController(BaseService<E, Long> entityService, BaseService<T, V> userEntityService) {
        this.entityService = entityService;
        this.userEntityService = userEntityService;
    }

    protected abstract void checkHasAccess(T entity, User user);

    protected abstract void checkHasOwnerAccess(T entity, User user);

    protected void checkIsNotFound(Long id) {
        if (entityService.getById(id) == null) {
            throw new NotFoundException().setResponseErrorMessage(new NotFoundResponseErrorMessage());
        }
    }


}
