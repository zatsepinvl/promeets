/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author MDay
 */

@Controller
public class HelloController 
{
    private static final Logger log = Logger.getLogger(HelloController.class);
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
   public String employee() 
   {
        return "index";
   }
}
