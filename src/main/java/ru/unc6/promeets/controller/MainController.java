package ru.unc6.promeets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Vladimir on 06.02.2016.
 */
@Controller
public class MainController {
    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }
}
