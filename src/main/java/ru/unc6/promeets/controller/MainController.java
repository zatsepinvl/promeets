package ru.unc6.promeets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

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
        return "index";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "index";
    }

    @RequestMapping(value = "/signup")
    public String signUp() {
        return "index";
    }


    @RequestMapping(value = "/group")
    public String group() {
        return "group";
    }

    @RequestMapping(value = "/group/{id}")
    public String group(@PathVariable long id) {
        return "index";
    }

    @RequestMapping(value = "/chat")
    public String chat() {
        return "chat";
    }

    @RequestMapping(value = "/meet/{id}")
    public String meetById(@PathVariable String id) {
        return "meet/" + id;
    }

    @RequestMapping(value = "/g")
    public String meetById() {
        return "index";
    }
}
