package com.rmstopa.challenge.service;

import com.rmstopa.challenge.model.Authority;
import com.rmstopa.challenge.model.Employee;
import com.rmstopa.challenge.model.Project;
import com.rmstopa.challenge.repository.DailyRepository;
import com.rmstopa.challenge.repository.EmployeeRepository;
import com.rmstopa.challenge.repository.MeetingRepository;
import com.rmstopa.challenge.security.SecurityUtils;
import liquibase.util.file.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.rmstopa.challenge.security.AuthoritiesConstants.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeSevice {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    DailyRepository dailyRepository;

    public List<Employee> getAllActiveAndStartesEmployess(){
        if(isEmployeeMenager()) {
            Authority authority = new Authority();
            authority.setName(STARTER);
            return employeeRepository.findEmployeesByAuthoritiesAndActiveTrueOrderByFirstName(authority);
        } else {
            return getScrumMasterEmployees();
        }
    }

    public List<Employee> getScrumMasterEmployees() {
        return meetingRepository.findAllEmployeesByScrumMasterId(getLoggedUser().getId())
                .stream()
                .filter(e -> e.isActive() == true)
                .sorted(Comparator.comparing(Employee::getFirstName))
                .collect(Collectors.toList());
    }


    public List<Employee> getAllActiveScrumMasterEmployess(){
        Authority authority = new Authority();
        authority.setName(SCRUM_MASTER);
        return employeeRepository.findEmployeesByAuthoritiesAndActiveTrueOrderByFirstName(authority);
    }

    public Employee saveEmployee(Employee employee, MultipartFile imageFile) throws Exception {

        if (employee.getFirstName().isBlank()){

            throw new Exception("Reveja as informações. Todos temos primeiros nomes.");
        }

        if (employee.getLastName().isBlank()){

            throw new Exception("Reveja as informações. Todos temos primeiros sobrenomes.");
        }

        if (employee.getFourLetters().isBlank()){

            throw new Exception("Reveja as informações. Precisa informar um as quatro letras.");
        }

        if (employee.getEmail().isBlank()){

            throw new Exception("Precisa informar um email.");
        }

        if(Optional.ofNullable(employee.getId()).isPresent()) {
            Employee oldEmployee = employeeRepository.findById(employee.getId()).orElseThrow();
            oldEmployee.setFirstName(employee.getFirstName());
            oldEmployee.setLastName(employee.getLastName());
            oldEmployee.setFourLetters(employee.getFourLetters());
            String fileName = saveImage(imageFile);
            oldEmployee.setImageUrl(fileName);
            return employeeRepository.save(oldEmployee);
        } else {

            if (employeeRepository.existsByEmailLike(employee.getEmail())){

                throw new Exception("Email ja utilizado. Tente outro.");
            }

            Set<Authority> authoritySet = new HashSet<>();
            Authority authority = new Authority();
            authority.setName(STARTER);
            authoritySet.add(authority);
            employee.setActive(true);
            employee.setAuthorities(authoritySet);
            String fileName = saveImage(imageFile);
            employee.setImageUrl(fileName);
            return employeeRepository.save(employee);
        }
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employee.setActive(false);
        employeeRepository.save(employee);
        dailyRepository.deleteAll(dailyRepository.findAllByEmployeeId(employee.getId()));
        projectService.deleteByEmployeeId(employee.getId());
    }

    public Employee getEmployee(Long id) throws Exception {

        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if(employeeOptional.isEmpty()) {

            throw new Exception("Starter não encontrado.");
        }
        return employeeOptional.get();
    }

    public Employee getLoggedUser () {
        return employeeRepository.findByLogin(SecurityUtils.getCurrentUserLogin().orElseThrow());
    }

    public Boolean isEmployeeMenager() {
        var employee = getLoggedUser();
        var auth = employee.getAuthorities();
        boolean employeeMenager = false;

        for (Authority authority: auth) {

            if (authority.getName().equals(MANAGER)) {
                employeeMenager = true;
            }
        }
            return employeeMenager;
    }

    public String saveImage(MultipartFile imageFile) throws Exception {
        if(FilenameUtils.getExtension(imageFile.getOriginalFilename()).contains("png")  || FilenameUtils.getExtension(imageFile.getOriginalFilename()).contains("jpeg")) {
        String folder = System.getProperty("user.dir") + "/src/main/resources/static/img/" ;
        byte[] bytes = imageFile.getBytes();
        String fileName = randomAlphanumeric(24)+"."+ FilenameUtils.getExtension(imageFile.getOriginalFilename());
        Path path = Paths.get(folder + fileName);
        Files.write(path, bytes);
        return "/img/" + fileName;
    } else {
        return null;
        }
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
}
