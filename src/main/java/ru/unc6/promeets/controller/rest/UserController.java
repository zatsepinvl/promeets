package ru.unc6.promeets.controller.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.controller.exception.BadRequestException;
import ru.unc6.promeets.controller.exception.ResponseError;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.service.entity.UserService;

import java.security.Principal;

/**
 * Created by Vladimir on 19.02.2016.
 */
@RestController
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);


    private static final String REPEATING_EMAIL_ERROR_MESSAGE = "User with the same email already exists.";

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public User user(Principal user) {
        return userService.getUserByEmail(user.getName());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()) != null) {
            throw new BadRequestException().setResponseError(new ResponseError(REPEATING_EMAIL_ERROR_MESSAGE));
        }
        return userService.save(user);
    }
}
