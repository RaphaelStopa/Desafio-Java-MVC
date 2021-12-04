package com.rmstopa.challenge.service;

import com.rmstopa.challenge.model.Module;
import com.rmstopa.challenge.model.StartProgram;
import com.rmstopa.challenge.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    DailyService dailyService;

    @Autowired
    MeetingService meetingService;

    @Autowired
    ProjectService projectService;

    public List<Module> getAllModules(){
        Sort sort = Sort.by("name");
        return moduleRepository.findAllByDeletedFalse(sort);
    }

    public Module saveModule(Module module) throws Exception {

        if (module.getName().isBlank()) {
            throw new Exception("Módulos precisam ter nomes. Por favor, selecione um nome.");
        }

        return moduleRepository.save(module);
    }

    public void deleteModule(Long id) {
        Module module = moduleRepository.findById(id).orElseThrow();
        module.setDeleted(true);
        moduleRepository.save(module);
        dailyService.deleteAllByModule(module.getId());
        projectService.deleteAllByModule(module.getId());
        meetingService.deleteAllByModule(module.getId());

    }

    public Module getModule(Long id) throws Exception {
        Optional<Module> moduleOptional = moduleRepository.findById(id);

        if(moduleOptional.isEmpty()) {

            throw new Exception("Módulo não encontrado");
        }
        return moduleOptional.get();
    }
}
