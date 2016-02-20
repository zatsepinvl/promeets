package ru.unc6.promeets.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;

/**
 * Created by Vladimir on 19.02.2016.
 */
@RestController
public class SecurityController {

    @RequestMapping(value = "security/data", method = RequestMethod.GET)
    public HashMap<String, String> getData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("location", "secured location");
        map.put("you", "so cool");
        return map;
    }

    @RequestMapping("security/user")
    public Principal user(Principal user) {
        return user;
    }

}
