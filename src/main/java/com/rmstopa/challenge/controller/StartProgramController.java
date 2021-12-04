package com.rmstopa.challenge.controller;

import com.rmstopa.challenge.model.StartProgram;
import com.rmstopa.challenge.service.StartProgramService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("start-program")
public class StartProgramController {

    private static final String MESSAGE = "message";

    private static final String FORM_PAGE = "startProgram/form.html";

    private static final String ATTRIBUTE_NAME = "startProgram";

    private final StartProgramService startProgramService;

    public StartProgramController(StartProgramService startProgramService) {
        this.startProgramService = startProgramService;
    }

    @RequestMapping
    public ModelAndView getAllStarterPrograms() {
        ModelAndView mv = new ModelAndView("startProgram/list.html");
        mv.addObject("list", startProgramService.getAllStartPrograms());
        return mv;
    }

    @RequestMapping(path = "new")
    public ModelAndView createStartProgram() {
        ModelAndView mv = new ModelAndView(FORM_PAGE);
        mv.addObject(ATTRIBUTE_NAME, new StartProgram());
        return mv;
    }

    @PostMapping("edit")
    public ModelAndView saveStartProgram(@Valid StartProgram startProgram, BindingResult bindingResult) {

        ModelAndView mv = new ModelAndView(FORM_PAGE);

        boolean newStartProgram = true;

        if (startProgram.getId() != null) {
            newStartProgram = false;
        }

        try {
            startProgramService.saveStartProgram(startProgram);
            mv.addObject(MESSAGE, "Programa starter salvo com sucesso");
        }catch (Exception e){
            mv.addObject(MESSAGE, e.getMessage());
        }


        if (bindingResult.hasErrors()) {
            mv.addObject(ATTRIBUTE_NAME, startProgram);
            return mv;
        }

        if (newStartProgram) {
            mv.addObject(ATTRIBUTE_NAME, new StartProgram());
        } else {
            mv.addObject(ATTRIBUTE_NAME, startProgram);
        }

        return mv;
    }

    @RequestMapping("edit")
    public ModelAndView updateStartProgram(@RequestParam(required = false) Long id) {

        ModelAndView mv = new ModelAndView(FORM_PAGE);

        StartProgram startProgram;

        if (id == null) {
            startProgram = new StartProgram();
        } else {
            try {
                startProgram = startProgramService.getStartProgram(id);
            } catch (Exception e) {
                startProgram = new StartProgram();
                mv.addObject(MESSAGE, e.getMessage());
            }
        }

        mv.addObject(ATTRIBUTE_NAME, startProgram);

        return mv;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteStartProgram(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/start-program");

        try {
            startProgramService.deleteStartProgram(id);
            redirectAttributes.addFlashAttribute(MESSAGE, "Programa starter excluído com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(MESSAGE, "Erro ao excluir. " + e.getMessage());
        }

        return mv;
    }

}
