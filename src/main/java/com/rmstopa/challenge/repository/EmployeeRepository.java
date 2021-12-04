package com.rmstopa.challenge.repository;

import com.rmstopa.challenge.model.Authority;
import com.rmstopa.challenge.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Optional<Employee> findOneWithAuthoritiesByLoginAndActiveTrue(String login);

    Employee findByLogin(String login);

    List<Employee> findEmployeesByAuthoritiesAndActiveTrueOrderByFirstName(Authority auth);

    Boolean existsByEmailLike(String email);
}


