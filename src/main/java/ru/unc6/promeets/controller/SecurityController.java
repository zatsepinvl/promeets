package ru.unc6.promeets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.repository.UserRepository;

import java.security.Principal;
import java.util.HashMap;

/**
 * Created by Vladimir on 19.02.2016.
 */
@RestController
public class SecurityController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public ResponseEntity<Object> user(Principal user) {
        return new ResponseEntity<Object>(userRepository.getUserByEmail(user.getName()), HttpStatus.OK);
    }

}
