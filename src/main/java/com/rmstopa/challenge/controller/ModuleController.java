package com.rmstopa.challenge.controller;

import com.rmstopa.challenge.model.Module;
import com.rmstopa.challenge.model.StartProgram;
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
@RequestMapping("module")
public class ModuleController {

    private static final String MESSAGE = "message";

    private static final String FORM_PAGE = "module/form.html";

    private static final String ATTRIBUTE_NAME = "module";

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @RequestMapping
    public ModelAndView getAllModule() {
        ModelAndView mv = new ModelAndView("module/list.html");
        mv.addObject("list", moduleService.getAllModules());
        return mv;
    }

    @RequestMapping(path = "new")
    public ModelAndView createModule() {
        ModelAndView mv = new ModelAndView(FORM_PAGE);
        mv.addObject("modules", moduleService.getAllModules());
        mv.addObject(ATTRIBUTE_NAME, new Module());
        return mv;
    }


    @PostMapping("edit")
    public ModelAndView saveModule(@Valid Module module, BindingResult bindingResult) {

        ModelAndView mv = new ModelAndView(FORM_PAGE);

        boolean newModule = true;

        if (module.getId() != null) {
            newModule = false;
        }

        try {
            moduleService.saveModule(module);
            mv.addObject(MESSAGE, "Módulo salvo com sucesso");
        } catch (Exception e) {
            mv.addObject(MESSAGE, e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            mv.addObject(ATTRIBUTE_NAME, module);
            return mv;
        }

        if (newModule) {
            mv.addObject(ATTRIBUTE_NAME, new StartProgram());
        } else {
            mv.addObject(ATTRIBUTE_NAME, module);
        }

        return mv;
    }

    @RequestMapping("edit")
    public ModelAndView updateStartProgram(@RequestParam(required = false) Long id) {

        ModelAndView mv = new ModelAndView(FORM_PAGE);

        Module module;

        if (id == null) {
            module = new Module();
        } else {
            try {
                module = moduleService.getModule(id);
            } catch (Exception e) {
                module = new Module();
                mv.addObject(MESSAGE, e.getMessage());
            }
        }

        mv.addObject(ATTRIBUTE_NAME, module);

        return mv;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteStartProgram(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/module");

        try {
            moduleService.deleteModule(id);
            redirectAttributes.addFlashAttribute(MESSAGE, "Módulo excluído com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(MESSAGE, "Erro ao excluir. " + e.getMessage());
        }

        return mv;
    }
}

