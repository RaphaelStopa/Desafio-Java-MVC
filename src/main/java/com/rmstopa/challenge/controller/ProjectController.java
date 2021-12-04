package com.rmstopa.challenge.controller;

import com.rmstopa.challenge.model.Project;
import com.rmstopa.challenge.service.EmployeeSevice;
import com.rmstopa.challenge.service.ModuleService;
import com.rmstopa.challenge.service.PdfService;
import com.rmstopa.challenge.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("project")
public class ProjectController {

    private static final String MESSAGE = "message";

    private static final String FORM_PAGE = "project/form.html";

    private static final String ATTRIBUTE_NAME = "project";

    private static final String MODULES = "modules";

    private static final String EMPLOYESS = "employees";

    private final ProjectService projectService;
    private final ModuleService moduleService;
    private final EmployeeSevice employeeSevice;
    private final PdfService pdfService;

    public ProjectController(ProjectService projectService, ModuleService moduleService, EmployeeSevice employeeSevice, PdfService pdfService) {
        this.projectService = projectService;
        this.moduleService = moduleService;
        this.employeeSevice = employeeSevice;
        this.pdfService = pdfService;
    }

    @RequestMapping
    public ModelAndView getAllProjects() {
        ModelAndView mv = new ModelAndView("project/list.html");
        mv.addObject("list", projectService.getAllProjects());
        return mv;
    }

    @RequestMapping(path = "new")
    public ModelAndView createProject() {
        ModelAndView mv = new ModelAndView(FORM_PAGE);

        mv.addObject(MODULES, moduleService.getAllModules());

        mv.addObject(EMPLOYESS, employeeSevice.getAllActiveAndStartesEmployess());

        mv.addObject(ATTRIBUTE_NAME, new Project());
        return mv;
    }

    @PostMapping("edit")
    public ModelAndView saveProject(@Valid Project project, BindingResult bindingResult){

        ModelAndView mv = new ModelAndView(FORM_PAGE);

        mv.addObject(MODULES, moduleService.getAllModules());
        mv.addObject(EMPLOYESS, employeeSevice.getAllActiveAndStartesEmployess());

        boolean newProject = true;

        if (project.getId() != null) {
            newProject = false;
        }

        try {
            projectService.saveProject(project);
            mv.addObject(MESSAGE, "Projeto salvo com sucesso");
        }catch (Exception e){
            mv.addObject(MESSAGE, e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            mv.addObject(ATTRIBUTE_NAME, project);
            return mv;
        }

        if (newProject) {
            mv.addObject(ATTRIBUTE_NAME, new Project());
        } else {
            mv.addObject(ATTRIBUTE_NAME, project);
        }



        return mv;
    }

    @RequestMapping("edit")
    public ModelAndView updateProject(@RequestParam(required = false) Long id) {

        ModelAndView mv = new ModelAndView(FORM_PAGE);

        mv.addObject(MODULES, moduleService.getAllModules());
        mv.addObject(EMPLOYESS, employeeSevice.getAllActiveAndStartesEmployess());

        Project project;

        if (id == null) {
            project = new Project();
        } else {
            try {
                project = projectService.getProject(id);
            } catch (Exception e) {
                project = new Project();
                mv.addObject(MESSAGE, e.getMessage());
            }
        }

        mv.addObject(ATTRIBUTE_NAME, project);

        return mv;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteProject(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/project");

        try {
            projectService.deleteProject(id);
            redirectAttributes.addFlashAttribute(MESSAGE, "Projeto excluído com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(MESSAGE, "Erro ao excluir. " + e.getMessage());
        }

        return mv;
    }

    @GetMapping("/download-pdf")
    public void downloadPDF(HttpServletResponse response) {
        try{
            Path file = Paths.get(pdfService.generatePdf().getAbsolutePath());
            if(Files.exists(file)) {
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition", "attachment; filename" + file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
