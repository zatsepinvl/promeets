package ru.unc6.promeets.controller.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.controller.exception.BadRequestException;
import ru.unc6.promeets.controller.exception.ResponseErrorMessage;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserInfo;
import ru.unc6.promeets.model.service.entity.UserInfoService;
import ru.unc6.promeets.model.service.entity.UserService;
import ru.unc6.promeets.security.CurrentUser;

import java.util.List;

/**
 * Created by Vladimir on 19.02.2016.
 */
@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController<User, Long> {
    private static final Logger log = Logger.getLogger(UserController.class);

    private static final String REPEATING_EMAIL_ERROR_MESSAGE = "User with the same email already exists.";

    @Autowired
    private UserInfoService userInfoService;

    private UserService userService;

    @Autowired
    public UserController(UserService service) {
        super(service);
        this.userService = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public User user(@CurrentUser User user) {
        return user;
    }

    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()) != null) {
            throw new BadRequestException().setResponseErrorMessage(new ResponseErrorMessage(REPEATING_EMAIL_ERROR_MESSAGE));
        }
        return userService.create(user);
    }

    @RequestMapping(value = "/{id}/info", method = RequestMethod.GET)
    public UserInfo getUserInfoByUserId(@PathVariable("id") long userId) {
        return userInfoService.getById(getAndCheckIsNotFoundById(userId).getUserId());
    }

    @RequestMapping(value = "/{id}/info", method = RequestMethod.PUT)
    public UserInfo updateUserInfoByUserId(@PathVariable("id") long userId, @RequestBody UserInfo userInfo) {
        checkIsNotFoundById(userId);
        userService.update(userInfo.getUser());
        return userInfoService.update(userInfo);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List searchUsers(@RequestParam(value = "firstName", required = false, defaultValue = "") String firstName, @RequestParam(value = "lastName", required = false, defaultValue = "") String lastName, @RequestParam(value = "email", required = false) String email, @CurrentUser User user) {
        if (email != null) {
            return userService.searchByEmail(email);
        } else {
            return userService.searchByFistNameAndLastName(firstName, lastName);
        }
    }
}



