package com.rmstopa.challenge.repository;

import com.rmstopa.challenge.model.Employee;
import com.rmstopa.challenge.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findAllByActiveTrue();

    @Query("SELECT m.employees FROM Meeting m WHERE m.scrumMaster.id = :id")
    List<Employee> findAllEmployeesByScrumMasterId(@Param("id") Long id);

    List<Meeting> findAllByModuleId(Long id);

    List<Meeting> findAllByStartProgramId(Long id);
}
