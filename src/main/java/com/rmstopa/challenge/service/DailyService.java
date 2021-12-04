package com.rmstopa.challenge.service;

import com.rmstopa.challenge.model.Daily;
import com.rmstopa.challenge.model.Employee;
import com.rmstopa.challenge.repository.DailyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DailyService {

    @Autowired
    DailyRepository dailyRepository;

    @Autowired
    EmployeeSevice employeeSevice;

    public List<Daily> getAllDailysByEmployeeActiveAndAuth(){
        if(employeeSevice.isEmployeeMenager()) {
            Sort sort = Sort.by("date").descending();
            return dailyRepository.findAll(sort).stream()
                    .filter(e -> e.getEmployee().isActive())
                    .collect(Collectors.toList());
        } else {
            return dailyRepository.findAllByEmployeeIdIn(employeeSevice.getScrumMasterEmployees()
                    .stream().map(Employee::getId).collect(Collectors.toList()))
                    .stream().sorted(Comparator.comparing(Daily::getDate).reversed()).collect(Collectors.toList());
        }
    }

    public Daily saveDaily(Daily daily) throws Exception {

        if (daily.getEmployee() == null){

            throw new Exception("Por favor, selecione ao menos um starter.");
        }

        daily.setDate(Instant.now());
        return dailyRepository.save(daily);
    }

    public void deleteDaily(Long id) {
        dailyRepository.deleteById(id);
    }

    public void deleteAllByModule(Long id) {
       dailyRepository.deleteAll(dailyRepository.findAllByModuleId(id));
    }

    public Daily getDaily(Long id) throws Exception {
        Optional<Daily> dailyOptional = dailyRepository.findById(id);

        if(dailyOptional.isEmpty()) {

            throw new Exception("Daily não encontrado.");
        }
        return dailyOptional.get();

    }

    public void deleteByEmployeeId(Long id) {
        dailyRepository.deleteAll(dailyRepository.findAllByEmployeeId(id));
    }
}
