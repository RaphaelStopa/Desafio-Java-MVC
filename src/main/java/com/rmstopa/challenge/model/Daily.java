package com.rmstopa.challenge.model;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "daily")
public class Daily implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "date")
    private Instant date;

    @Column(name = "making")
    private String making;

    @Column(name = "done")
    private String done;

    @Column(name = "impediment")
    private String impediment;

    @NotNull
    @Column(name = "presence")
    private Boolean presence = false;

    @ManyToOne
    @JoinColumn
    private Module module;

    @ManyToOne
    @JoinColumn
    private Employee employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getMaking() {
        return making;
    }

    public void setMaking(String making) {
        this.making = making;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public String getImpediment() {
        return impediment;
    }

    public void setImpediment(String impediment) {
        this.impediment = impediment;
    }

    public Boolean getPresence() {
        return presence;
    }

    public void setPresence(Boolean presence) {
        this.presence = presence;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Daily{" +
                "id=" + id +
                ", date=" + date +
                ", making='" + making + '\'' +
                ", done='" + done + '\'' +
                ", impediment='" + impediment + '\'' +
                ", presence=" + presence +
                ", module=" + module +
                ", employee=" + employee +
                '}';
    }
}
