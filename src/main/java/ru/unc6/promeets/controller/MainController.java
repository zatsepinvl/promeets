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

    private static final String INDEX_PATH = "/static/index.html";

    @RequestMapping(value = "/security")
    public String security() {
        return "security";
    }

    @RequestMapping(value = "/")
    public String index() {
        return INDEX_PATH;
    }

    @RequestMapping(value = "/login")
    public String login() {
        return INDEX_PATH;
    }

    @RequestMapping(value = "/signup")
    public String signUp() {
        return INDEX_PATH;
    }

    @RequestMapping(value = "/group")
    public String group() {
        return INDEX_PATH;
    }

    @RequestMapping(value = "/group/{id}")
    public String group(@PathVariable long id) {
        return INDEX_PATH;
    }

    @RequestMapping(value = "/chat")
    public String chat() {
        return INDEX_PATH;
    }

    @RequestMapping(value = "/meet/{id}")
    public String meetById(@PathVariable String id) {
        return "meet/" + id;
    }
}
