package com.rmstopa.challenge.repository;

import com.rmstopa.challenge.model.Module;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    List<Module> findAllByDeletedFalse(Sort sort);
}
