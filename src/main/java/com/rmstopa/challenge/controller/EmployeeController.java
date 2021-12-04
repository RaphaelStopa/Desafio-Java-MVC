package com.rmstopa.challenge.controller;
import com.rmstopa.challenge.model.Employee;
import com.rmstopa.challenge.model.StartProgram;
import com.rmstopa.challenge.service.EmployeeSevice;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("employee")
public class EmployeeController {

    private static final String MESSAGE = "message";

    private static final String FORM_PAGE = "employee/form.html";

    private static final String ATTRIBUTE_NAME = "employee";

    private final EmployeeSevice employeeSevice;

    public EmployeeController(EmployeeSevice employeeSevice) {
        this.employeeSevice = employeeSevice;
    }

    @RequestMapping
    public ModelAndView getAllEmployeesStarters() {
        ModelAndView mv = new ModelAndView("employee/list.html");
        mv.addObject("list", employeeSevice.getAllActiveAndStartesEmployess());
        return mv;
    }

    @RequestMapping(path = "new")
    public ModelAndView createEmployee() {
        ModelAndView mv = new ModelAndView(FORM_PAGE);
        mv.addObject(ATTRIBUTE_NAME, new Employee());
        return mv;
    }

    //We should be using DTO`s but I'm doing the same as the company's tutorial
    @PostMapping("edit")
    public ModelAndView saveEmployee(@Valid Employee employee, @RequestParam("imageFile") MultipartFile imageFile, BindingResult bindingResult){
        ModelAndView mv = new ModelAndView(FORM_PAGE);

        boolean newEmploye = true;

        if (employee.getId() != null) {
            newEmploye = false;
        }

        mv.addObject(MESSAGE, "Starter salvo com sucesso");

        try {
            employeeSevice.saveEmployee(employee, imageFile);
        } catch (Exception e) {
            mv.addObject(MESSAGE, e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            mv.addObject(ATTRIBUTE_NAME, employee);
            return mv;
        }


        if (newEmploye) {
            mv.addObject(ATTRIBUTE_NAME, new Employee());
        } else {
            mv.addObject(ATTRIBUTE_NAME, employee);
        }

        return mv;
    }

    @RequestMapping("edit")
    public ModelAndView updateEmployee(@RequestParam(required = false) Long id) {

        ModelAndView mv = new ModelAndView(FORM_PAGE);

        Employee employee;

        if (id == null) {
            employee = new Employee();
        } else {
            try {
                employee = employeeSevice.getEmployee(id);
            } catch (Exception e) {
                employee = new Employee();
                mv.addObject(MESSAGE, e.getMessage());
            }
        }

        mv.addObject(ATTRIBUTE_NAME, employee);

        return mv;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteEmployee(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/employee");

        try {
            employeeSevice.deleteEmployee(id);
            redirectAttributes.addFlashAttribute(MESSAGE, "Starter excluído com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(MESSAGE, "Erro ao excluir. " + e.getMessage());
        }
        return mv;
    }
}
