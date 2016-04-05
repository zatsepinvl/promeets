package ru.unc6.promeets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String groups() {
        return INDEX_PATH;
    }

    @RequestMapping(value = "/group/{id}")
    public String groupPage(@PathVariable long id) {
        return INDEX_PATH;
    }

    @RequestMapping(value = "/group/{id}/calendar")
    public String groupCalendar(@PathVariable long id) {
        return INDEX_PATH;
    }

    @RequestMapping(value = "/chat")
    public String chat() {
        return "chat";
    }

    @RequestMapping(value = "/meet/{id}")
    public String meetById(@PathVariable String id) {
        return INDEX_PATH;
    }

    @RequestMapping(value = "/venue/{id}")
    public String venueByMeetId(@PathVariable String id) {
        return INDEX_PATH;
    }
}
