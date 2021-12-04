package com.rmstopa.challenge.controller;

import com.rmstopa.challenge.service.EmployeeSevice;
import com.rmstopa.challenge.service.mail.MailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    private final EmployeeSevice employeeSevice;
    private final MailService mailService;

    public IndexController(EmployeeSevice employeeSevice, MailService mailService) {
        this.employeeSevice = employeeSevice;
        this.mailService = mailService;
    }

    @RequestMapping
    public ModelAndView managerPage(){
        if(employeeSevice.isEmployeeMenager()) {
            ModelAndView mv = new ModelAndView("index.html");
            return mv;
        } else {
            var employee = employeeSevice.getLoggedUser();
            mailService.sendEmail(employee.getFirstName(), employee.getLastName());
            ModelAndView mv = new ModelAndView("redirect:/scrum");
            return mv;
        }
    }

}
