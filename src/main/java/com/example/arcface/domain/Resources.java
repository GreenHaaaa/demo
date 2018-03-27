package com.example.arcface.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Resources {
    @Id
    private Integer id;
    @JsonIgnore
    @ManyToMany(mappedBy = "resourcesSet")
    private Set<Task> tasks;
    @Column
    private String path;

    public Resources() {
    }

    public Resources(Integer id) {
        this.id = id;
        this.tasks = new HashSet<Task>();;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
