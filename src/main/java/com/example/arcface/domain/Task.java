package com.example.arcface.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id ;
    @Column
    @NotNull
    private String taskName;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TASK_RESOURCE", joinColumns = {
            @JoinColumn(name = "TASK_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "RESOURCE_ID", referencedColumnName = "ID")})
    private Set<Resources> resourcesSet;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TASK_USER", joinColumns = {
            @JoinColumn(name = "TASK_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "WORKNUMBER")})
    private Set<User> users;
    @NotNull
    @ManyToOne
    @JoinColumn(name ="PROJECT_ID")
    private Project project ;
    @ManyToOne
    @JoinColumn(name ="USER_ID")
    private User taskPublisher;
    @Column
    private String taskBegin;
    @Column
    private  String taskEnd;
    @Column
    private int taskStatus;
    @Column
    private int securityLv;
    @Column(length = 10000)
    private String taskContent;
    @Column
    private int workload;
    public Task() {
    }

    public Task( int securityLv,User user,String taskName, String taskContent,int workload,Project project,String taskBegin,String taskEnd,int taskStatus) {
        this.id = 1;
        this.taskName = taskName;
        this.project = project;
        this.securityLv = securityLv;
        this.taskContent = taskContent;
        this.workload = workload;
        this.taskBegin = taskBegin;
        this.taskEnd = taskEnd;
        this.taskPublisher = user;
        this.taskStatus = taskStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Resources> getResourcesSet() {
        return resourcesSet;
    }

    public void setResourcesSet(Set<Resources> resourcesSet) {
        this.resourcesSet = resourcesSet;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public int getSecurityLv() {
        return securityLv;
    }

    public void setSecurityLv(int securityLv) {
        this.securityLv = securityLv;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public int getWorkload() {
        return workload;
    }

    public void setWorkload(int workload) {
        this.workload = workload;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getTaskBegin() {
        return taskBegin;
    }

    public void setTaskBegin(String taskBegin) {
        this.taskBegin = taskBegin;
    }

    public String getTaskEnd() {
        return taskEnd;
    }

    public void setTaskEnd(String taskEnd) {
        this.taskEnd = taskEnd;
    }

    public User getTaskPublisher() {
        return taskPublisher;
    }

    public void setTaskPublisher(User taskPublisher) {
        this.taskPublisher = taskPublisher;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskname) {
        this.taskName = taskname;
    }
}
