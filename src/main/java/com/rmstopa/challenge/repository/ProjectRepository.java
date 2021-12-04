package com.rmstopa.challenge.repository;


import com.rmstopa.challenge.model.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p ORDER BY p.evaluation DESC")
    List<Project> findLowestGrade(Pageable pageable);

    @Query("SELECT p FROM Project p ORDER BY p.evaluation ASC")
    List<Project> findHighestGrade(Pageable pageable);


    @Query("select p from Project p where p.employee.active = true")
    List<Project>findProjectsByEmployeeActive();

    List<Project> findAllByModuleId(Long id);

    List<Project> findAllByEmployeeId(Long id);
}
