package com.rmstopa.challenge.service;

import com.rmstopa.challenge.model.Project;
import com.rmstopa.challenge.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    public List<Project> getAllProjects(){
        return projectRepository.findProjectsByEmployeeActive();
    }

    public Project saveProject(Project project) throws Exception {

        if (!Optional.ofNullable(project.getEmployee()).isPresent()){

            throw new Exception("Projetos precisam ter donos. Por favor, selecione um starter.");
        }

        if (!Optional.ofNullable(project.getEvaluation()).isPresent()){

            throw new Exception("Projetos precisam ter nota. Por favor, selecione a nota do starter.");
        }

        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
    public void deleteAllByModule(Long id) {
        projectRepository.deleteAll(projectRepository.findAllByModuleId(id));
    }

    public Project getProject(Long id) throws Exception{
        Optional<Project> projectOptional = projectRepository.findById(id);

        if(projectOptional.isEmpty()) {

            throw new Exception("Projeto não encontrado");
        }
        return projectOptional.get();
    }

    public List<Project> getProjectsWithLowestGrades() {

        return projectRepository.findLowestGrade(PageRequest.of(0,5));
    }

    public List<Project> getProjectsHighestGrades() {

        return projectRepository.findHighestGrade(PageRequest.of(0,5));
    }

    public void deleteByEmployeeId(Long id) {
        projectRepository.deleteAll(projectRepository.findAllByEmployeeId(id));
    }
}
