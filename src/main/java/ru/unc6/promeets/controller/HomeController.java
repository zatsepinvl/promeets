package ru.unc6.promeets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.controller.exception.BadRequestException;
import ru.unc6.promeets.controller.exception.ResponseErrorMessage;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.service.entity.UserService;

/**
 * Created by Vladimir on 24.05.2016.
 */
@RestController
public class HomeController {
    private static final String REPEATING_EMAIL_ERROR_MESSAGE = "User with the same email already exists.";

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public User signUp(@RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()) != null) {
            throw new BadRequestException().setResponseErrorMessage(new ResponseErrorMessage(REPEATING_EMAIL_ERROR_MESSAGE));
        }
        return userService.create(user);
    }
}
