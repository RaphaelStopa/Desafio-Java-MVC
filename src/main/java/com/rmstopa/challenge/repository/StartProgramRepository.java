package com.rmstopa.challenge.repository;

import com.rmstopa.challenge.model.StartProgram;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StartProgramRepository extends JpaRepository<StartProgram, Long> {
    List<StartProgram> findAllByDeletedFalse(Sort sort);
}
