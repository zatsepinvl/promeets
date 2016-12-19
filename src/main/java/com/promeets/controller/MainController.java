package com.promeets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Vladimir on 06.02.2016.
 */
@Controller
public class MainController {

    private static final String INDEX_PATH = "/static/index.html";

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

    @RequestMapping(value = "/group/**")
    public String groupPage() {
        return INDEX_PATH;
    }

    @RequestMapping(value = "/groups")
    public String groups() {
        return INDEX_PATH;
    }

    @RequestMapping(value = "/messages")
    public String messages() {
        return INDEX_PATH;
    }

    @RequestMapping(value = "/calendar")
    public String calendar() {
        return INDEX_PATH;
    }

    @RequestMapping(value = "/venue/*")
    public String venueByMeetId() {
        return INDEX_PATH;
    }

    @RequestMapping(value = "/files")
    public String fileTest() {
        return INDEX_PATH;
    }

    @RequestMapping(value = "/profile/*")
    public String profile() {
        return INDEX_PATH;
    }
}
