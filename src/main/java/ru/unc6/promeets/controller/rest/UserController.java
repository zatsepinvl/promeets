package ru.unc6.promeets.controller.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.controller.exception.BadRequestException;
import ru.unc6.promeets.controller.exception.ResponseError;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.service.entity.UserService;
import ru.unc6.promeets.security.CurrentUser;
import ru.unc6.promeets.security.CustomUserDetails;

import java.security.Principal;

/**
 * Created by Vladimir on 19.02.2016.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);


    private static final String REPEATING_EMAIL_ERROR_MESSAGE = "User with the same email already exists.";

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public User user(@CurrentUser User user) {
        return user;
    }

    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()) != null) {
            throw new BadRequestException().setResponseError(new ResponseError(REPEATING_EMAIL_ERROR_MESSAGE));
        }
        return userService.save(user);
    }
}
