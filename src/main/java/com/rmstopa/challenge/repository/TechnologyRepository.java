package com.rmstopa.challenge.repository;

import com.rmstopa.challenge.model.Technology;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    List<Technology> findAllByDeletedFalse(Sort sort);
}
