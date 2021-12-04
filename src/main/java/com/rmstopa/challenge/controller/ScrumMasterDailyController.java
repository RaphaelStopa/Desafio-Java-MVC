package com.rmstopa.challenge.controller;

import com.rmstopa.challenge.model.Daily;
import com.rmstopa.challenge.service.DailyService;
import com.rmstopa.challenge.service.EmployeeSevice;
import com.rmstopa.challenge.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("scrum")
public class ScrumMasterDailyController {

    private static final String MESSAGE = "message";

    private static final String FORM_PAGE = "dailyScrumMaster/form.html";

    private static final String ATTRIBUTE_NAME = "daily";

    private static final String MODULES = "modules";

    private static final String EMPLOYESS = "employees";

    private final DailyService dailyService;
    private final ModuleService moduleService;
    private final EmployeeSevice employeeSevice;

    public ScrumMasterDailyController(DailyService dailyService, ModuleService moduleService, EmployeeSevice employeeSevice) {
        this.dailyService = dailyService;
        this.moduleService = moduleService;
        this.employeeSevice = employeeSevice;
    }

    @RequestMapping()
    public ModelAndView scrumPage(){
        ModelAndView mv = new ModelAndView("dailyScrumMaster/list.html");
        mv.addObject("list", dailyService.getAllDailysByEmployeeActiveAndAuth());
        return mv;
    }

    @RequestMapping(path = "new")
    public ModelAndView createDaily() {
        ModelAndView mv = new ModelAndView(FORM_PAGE);

        mv.addObject(MODULES, moduleService.getAllModules());
        mv.addObject(EMPLOYESS, employeeSevice.getAllActiveAndStartesEmployess());

        mv.addObject(ATTRIBUTE_NAME, new Daily());
        return mv;
    }

    @PostMapping("edit")
    public ModelAndView saveDaily(@Valid Daily daily, BindingResult bindingResult) {

        ModelAndView mv = new ModelAndView(FORM_PAGE);

        boolean newDaily = true;

        if (daily.getId() != null) {
            newDaily = false;
        }


        if (bindingResult.hasErrors()) {
            mv.addObject(ATTRIBUTE_NAME, daily);
            return mv;
        }

        try {
            dailyService.saveDaily(daily);
            mv.addObject(MESSAGE, "Daily salva com sucesso");
        } catch (Exception e) {
            mv.addObject(MESSAGE, e.getMessage());
        }

        mv.addObject(MODULES, moduleService.getAllModules());

        mv.addObject(EMPLOYESS, employeeSevice.getAllActiveAndStartesEmployess());

        if (newDaily) {
            mv.addObject(ATTRIBUTE_NAME, new Daily());
        } else {
            mv.addObject(ATTRIBUTE_NAME, daily);
        }




        return mv;
    }

    @RequestMapping("edit")
    public ModelAndView updateDaily(@RequestParam(required = false) Long id) {

        ModelAndView mv = new ModelAndView(FORM_PAGE);

        mv.addObject(MODULES, moduleService.getAllModules());

        mv.addObject(EMPLOYESS, employeeSevice.getAllActiveAndStartesEmployess());

        Daily daily;

        if (id == null) {
            daily = new Daily();
        } else {
            try {
                daily = dailyService.getDaily(id);
            } catch (Exception e) {
                daily = new Daily();
                mv.addObject(MESSAGE, e.getMessage());
            }
        }

        mv.addObject(ATTRIBUTE_NAME, daily);

        return mv;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteDaily(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/scrum");

        try {
            dailyService.deleteDaily(id);
            redirectAttributes.addFlashAttribute(MESSAGE, "Daily excluída com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(MESSAGE, "Erro ao excluir. " + e.getMessage());
        }

        return mv;
    }
}
