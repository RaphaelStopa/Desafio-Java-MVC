package com.rmstopa.challenge.controller;

import com.rmstopa.challenge.model.Meeting;
import com.rmstopa.challenge.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("group")
public class MeetingController {

    private static final String MESSAGE = "message";

    private static final String FORM_PAGE = "group/form.html";

    private static final String ATTRIBUTE_NAME = "group";

    private static final String SCRUM_MASTERS = "scrumMasters";

    private static final String EMPLOYESS = "employees";

    private static final String PROGRAMS = "programs";

    private static final String TECHNOLOGIES = "technologies";

    private static final String MODULES = "modules";

    private final MeetingService meetingService;
    private final EmployeeSevice employeeSevice;
    private final TechnologyService technologyService;
    private final ModuleService moduleService;
    private final StartProgramService startProgramService;

    public MeetingController(MeetingService meetingService, EmployeeSevice employeeSevice, TechnologyService technologyService, ModuleService moduleService, StartProgramService startProgramService) {
        this.meetingService = meetingService;
        this.employeeSevice = employeeSevice;
        this.technologyService = technologyService;
        this.moduleService = moduleService;
        this.startProgramService = startProgramService;
    }

    @RequestMapping
    public ModelAndView getAllMeetings() {
        ModelAndView mv = new ModelAndView("group/list.html");
        mv.addObject("list", meetingService.getAllActiveMeetings());
        return mv;
    }

    @RequestMapping(path = "new")
    public ModelAndView createMeeting() {
        ModelAndView mv = new ModelAndView(FORM_PAGE);

        mv.addObject(EMPLOYESS, employeeSevice.getAllActiveAndStartesEmployess());

        mv.addObject(SCRUM_MASTERS, employeeSevice.getAllActiveScrumMasterEmployess());

        mv.addObject(TECHNOLOGIES, technologyService.getAllTechnologies());

        mv.addObject(MODULES, moduleService.getAllModules());

        mv.addObject(PROGRAMS, startProgramService.getAllStartPrograms());

        mv.addObject(ATTRIBUTE_NAME, new Meeting());
        return mv;
    }


    @PostMapping("edit")
    public ModelAndView saveMeeting(@Valid Meeting meeting, BindingResult bindingResult) {

        ModelAndView mv = new ModelAndView(FORM_PAGE);

        boolean newMeeting = true;

        if (meeting.getId() != null) {
            newMeeting = false;
        }

        try {
            meetingService.saveMeeting(meeting);
            mv.addObject(MESSAGE, "Grupo salvo com sucesso");
        }catch (Exception e){
            mv.addObject(MESSAGE, e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            mv.addObject(ATTRIBUTE_NAME, meeting);
            return mv;
        }

        mv.addObject(EMPLOYESS, employeeSevice.getAllActiveAndStartesEmployess());

        mv.addObject(SCRUM_MASTERS, employeeSevice.getAllActiveScrumMasterEmployess());

        mv.addObject(TECHNOLOGIES, technologyService.getAllTechnologies());

        mv.addObject(MODULES, moduleService.getAllModules());

        mv.addObject(PROGRAMS, startProgramService.getAllStartPrograms());

        if (newMeeting) {
            mv.addObject(ATTRIBUTE_NAME, new Meeting());
        } else {
            mv.addObject(ATTRIBUTE_NAME, meeting);
        }
        return mv;
    }

    @RequestMapping("edit")
    public ModelAndView updateMeeting(@RequestParam(required = false) Long id) {

        ModelAndView mv = new ModelAndView(FORM_PAGE);

        mv.addObject(EMPLOYESS, employeeSevice.getAllActiveAndStartesEmployess());

        mv.addObject(SCRUM_MASTERS, employeeSevice.getAllActiveScrumMasterEmployess());

        mv.addObject(TECHNOLOGIES, technologyService.getAllTechnologies());

        mv.addObject(MODULES, moduleService.getAllModules());

        mv.addObject(PROGRAMS, startProgramService.getAllStartPrograms());

        Meeting meeting;

        if (id == null) {
            meeting = new Meeting();
        } else {
            try {
                meeting = meetingService.getMeeting(id);
            } catch (Exception e) {
                meeting = new Meeting();
                mv.addObject(MESSAGE, e.getMessage());
            }
        }

        mv.addObject(ATTRIBUTE_NAME, meeting);

        return mv;
    }


    @RequestMapping("/delete")
    public ModelAndView deleteMeeting(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/group");

        try {
            meetingService.deleteMeeting(id);
            redirectAttributes.addFlashAttribute(MESSAGE, "Grupo excluído com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(MESSAGE, "Erro ao excluir. " + e.getMessage());
        }

        return mv;
    }
}
