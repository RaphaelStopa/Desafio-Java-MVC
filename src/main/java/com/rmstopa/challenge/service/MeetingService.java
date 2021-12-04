package com.rmstopa.challenge.service;


import com.rmstopa.challenge.model.Employee;
import com.rmstopa.challenge.model.Meeting;
import com.rmstopa.challenge.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingService {

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    EmployeeSevice employeeSevice;

    public List<Meeting> getAllActiveMeetings(){

        return meetingRepository.findAllByActiveTrue();
    }

    public Meeting saveMeeting(Meeting meeting) throws Exception {

        if (meeting.getEmployees().isEmpty()){

            throw new Exception("Grupos precisam ter pessoas. Por favor, selecione ao menos um starter.");
        }

        return meetingRepository.save(meeting);
    }


    public Meeting getMeeting(Long id) throws Exception {
        Optional<Meeting> meetingOptional = meetingRepository.findById(id);

        if(meetingOptional.isEmpty()) {

            throw new Exception("Grupo não encontrado.");
        }
        return meetingOptional.get();

    }

    public void deleteMeeting(Long id) {
        Meeting meeting= meetingRepository.findById(id).orElseThrow();
        meeting.setActive(false);
        meetingRepository.save(meeting);
    }


    public void deleteAllByModule(Long id) {
        List<Meeting> meetings = meetingRepository.findAllByModuleId(id);
        meetings.stream().forEach(meeting -> {meeting.setActive(false);meetingRepository.save(meeting);});
    }

    public void deleteAllByStartProgram(Long id) {
        List<Meeting> meetings = meetingRepository.findAllByStartProgramId(id);
        meetings.stream().forEach(meeting -> {meeting.setActive(false);meetingRepository.save(meeting);
            meeting.getEmployees().forEach(employee -> {employeeSevice.deleteEmployee(employee.getId());});});
    }
}
