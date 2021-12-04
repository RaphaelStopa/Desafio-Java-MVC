package com.rmstopa.challenge.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn
    private StartProgram startProgram;

    @NotNull
    @ManyToOne
    @JoinColumn
    private Technology technology;

    @NotNull
    @ManyToOne
    @JoinColumn
    private Employee scrumMaster;

    @NotNull
    @ManyToOne
    @JoinColumn
    private Module module;

    @NotNull
    @Column(nullable = false)
    private boolean active = true;

    @ManyToMany
    @JoinTable(
            name = "meeting_employee",
            joinColumns = { @JoinColumn(name = "meeting_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "employee_id", referencedColumnName = "id") }
    )
    @BatchSize(size = 20)
    private Set<Employee> employees = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StartProgram getStartProgram() {
        return startProgram;
    }

    public void setStartProgram(StartProgram startProgram) {
        this.startProgram = startProgram;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public Employee getScrumMaster() {
        return scrumMaster;
    }

    public void setScrumMaster(Employee scrumMaster) {
        this.scrumMaster = scrumMaster;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", startProgram=" + startProgram +
                ", technology=" + technology +
                ", scrumMaster=" + scrumMaster +
                ", module=" + module +
                ", active=" + active +
                ", employees=" + employees +
                '}';
    }
}
