package ru.unc6.promeets.controller.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.repository.UserRepository;
import ru.unc6.promeets.model.service.UserService;

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
    public ResponseEntity<Object> user(Principal user) {
        return new ResponseEntity<Object>(userService.getUserByEmail(user.getName()), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ResponseEntity<ControllerError> createUser(@RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()) == null) {
            try {
                userService.save(user);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return new ResponseEntity<>(new ControllerError(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(new ControllerError(REPEATING_EMAIL_ERROR_MESSAGE), HttpStatus.BAD_REQUEST);
        }
    }
}
