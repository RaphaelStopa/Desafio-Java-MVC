package com.rmstopa.challenge.service;

import com.rmstopa.challenge.model.StartProgram;
import com.rmstopa.challenge.repository.StartProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StartProgramService {

    @Autowired
    StartProgramRepository startProgramRepository;

    @Autowired
    DailyService dailyService;

    @Autowired
    ProjectService projectService;

    @Autowired
    MeetingService meetingService;

    public List<StartProgram> getAllStartPrograms(){
        Sort sort = Sort.by("name");
        return startProgramRepository.findAllByDeletedFalse(sort);
    }

    public StartProgram saveStartProgram(StartProgram startProgram) throws Exception {
        if (startProgram.getName().isBlank()) {
            throw new Exception("Programas precisam ter nomes. Por favor, selecione um nome.");
        }
        return startProgramRepository.save(startProgram);
    }

    public void deleteStartProgram(Long id) {
        StartProgram startProgram = startProgramRepository.findById(id).orElseThrow();
        startProgram.setDeleted(true);
        startProgramRepository.save(startProgram);
        meetingService.deleteAllByStartProgram(startProgram.getId());
    }

    public StartProgram getStartProgram(Long id) throws Exception{
        Optional<StartProgram> startProgramOptional = startProgramRepository.findById(id);

        if(startProgramOptional.isEmpty()) {

            throw new Exception("Programa não encontrado");
        }
        return startProgramOptional.get();
    }
}
