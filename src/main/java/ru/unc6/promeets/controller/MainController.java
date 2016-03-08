package ru.unc6.promeets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Vladimir on 06.02.2016.
 */
@Controller
public class MainController {

    @RequestMapping(value = "/security")
    public String security() {
        return "security";
    }

    @RequestMapping(value = "/")
    public String index() {
        return "login";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/group")
    public String group() {
        return "group";
    }
    
    @RequestMapping(value = "/chat")
    public String chat() {
        return "chat";
    }

    @RequestMapping(value = "/edit_meet/{id}")
    public String group(@PathVariable long id) {
        return "edit_meet/" + id;
    }

}
