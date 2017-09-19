package com.warrior.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(value = {"","/index"})
    public String login(){
        return "index_prod";
    }

    @RequestMapping(value="/unauth")
    public String unauth(){
        return "unauth";
    }
}