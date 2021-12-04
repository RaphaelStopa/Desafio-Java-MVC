package com.rmstopa.challenge.controller;

import com.rmstopa.challenge.model.Technology;
import com.rmstopa.challenge.service.TechnologyService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("technology")
public class TechnologyController {

    private static final String MESSAGE = "message";

    private static final String FORM_PAGE = "technology/form.html";

    private static final String ATTRIBUTE_NAME = "technology";

    private final TechnologyService technologyService;

    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @RequestMapping
    public ModelAndView getAllTechnologies() {
        ModelAndView mv = new ModelAndView("technology/list.html");
        mv.addObject("list", technologyService.getAllTechnologies());
        return mv;
    }

    @RequestMapping(path = "new")
    public ModelAndView createTechnology() {
        ModelAndView mv = new ModelAndView(FORM_PAGE);
        mv.addObject(ATTRIBUTE_NAME, new Technology());
        return mv;
    }


    @PostMapping("edit")
    public ModelAndView saveTechnology(@Valid Technology technology, BindingResult bindingResult) {

        ModelAndView mv = new ModelAndView(FORM_PAGE);

        boolean newTechnology = true;

        if (technology != null) {
            newTechnology = false;
        }

        try {
            technologyService.saveTechnology(technology);
            mv.addObject(MESSAGE, "Tecnologia salva com sucesso");
        } catch (Exception e) {
            mv.addObject(MESSAGE, e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            mv.addObject(ATTRIBUTE_NAME, technology);
            return mv;
        }

        if (newTechnology) {
            mv.addObject(ATTRIBUTE_NAME, new Technology());
        } else {
            mv.addObject(ATTRIBUTE_NAME, technology);
        }

        return mv;
    }

    @RequestMapping("edit")
    public ModelAndView updateTechnology(@RequestParam(required = false) Long id) {

        ModelAndView mv = new ModelAndView(FORM_PAGE);

        Technology technology;

        if (id == null) {
            technology = new Technology();
        } else {
            try {
                technology = technologyService.getTechnology(id);
            } catch (Exception e) {
                technology = new Technology();
                mv.addObject(MESSAGE, e.getMessage());
            }
        }

        mv.addObject(ATTRIBUTE_NAME, technology);

        return mv;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteTechnology(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/technology");

        try {
            technologyService.deleteTechnology(id);
            redirectAttributes.addFlashAttribute(MESSAGE, "Tecnologia excluída com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(MESSAGE, "Erro ao excluir. " + e.getMessage());
        }

        return mv;
    }
}
