package com.rmstopa.challenge.security;

import com.rmstopa.challenge.model.Employee;
import com.rmstopa.challenge.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return employeeRepository
                .findOneWithAuthoritiesByEmailIgnoreCase(login)
                .map(user -> createSpringSecurityUser(user))
                .orElseGet(
                        () ->
                                employeeRepository
                                        .findOneWithAuthoritiesByLoginAndActiveTrue(login.toLowerCase())
                                        .map(user -> createSpringSecurityUser(user))
                                        .orElseThrow(() -> new UsernameNotFoundException("User " + login + " was not found in the database"))
                );
    }

    private User createSpringSecurityUser(Employee employee) {
        List<GrantedAuthority> grantedAuthorities = employee
                .getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new User(employee.getLogin(), employee.getPassword(), grantedAuthorities);
    }
}
