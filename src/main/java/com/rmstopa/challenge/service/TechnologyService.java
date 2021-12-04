package com.rmstopa.challenge.service;

import com.rmstopa.challenge.model.Technology;
import com.rmstopa.challenge.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnologyService {

    @Autowired
    TechnologyRepository technologyRepository;

    public List<Technology> getAllTechnologies(){
        Sort sort = Sort.by("name");
        return technologyRepository.findAllByDeletedFalse(sort);
    }

    public Technology saveTechnology(Technology technology) throws Exception {

        if (technology.getDescription().isBlank()) {
            throw new Exception("Precisa de uma descrição. Por favor, escreva uma.");
        }

        return technologyRepository.save(technology);
    }


    public void deleteTechnology(Long id) {
        Technology technology = technologyRepository.findById(id).orElseThrow();
        technology.setDeleted(true);
        technologyRepository.save(technology);
    }

    public Technology getTechnology(Long id) throws Exception{
        Optional<Technology> technologyOptional = technologyRepository.findById(id);

        if(technologyOptional.isEmpty()) {

            throw new Exception("Tecnologia não encontrada");
        }
        return technologyOptional.get();
    }
}
