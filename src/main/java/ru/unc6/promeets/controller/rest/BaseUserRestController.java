package ru.unc6.promeets.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.unc6.promeets.controller.exception.NotFoundException;
import ru.unc6.promeets.controller.exception.NotFoundResponseErrorMessage;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.service.entity.BaseService;
import ru.unc6.promeets.security.CurrentUser;

import java.io.Serializable;
import java.util.List;

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
