package com.rmstopa.challenge.repository;

import com.rmstopa.challenge.model.Daily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyRepository extends JpaRepository<Daily, Long> {

    List<Daily> findAllByEmployeeIdIn(List<Long> ids);

    List<Daily> findAllByModuleId(Long id);

    List<Daily> findAllByEmployeeId(Long id);
}
