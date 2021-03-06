package com.rmstopa.challenge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("login")
public class LoginController {

    @RequestMapping
    public ModelAndView authorize(@RequestParam(required = false) String error) {
        ModelAndView mv = new ModelAndView("login.html");
        mv.addObject("error", error);
        return mv;
    }
}
