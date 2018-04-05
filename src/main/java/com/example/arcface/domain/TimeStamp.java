package com.example.arcface.domain;

import javax.persistence.*;


@Entity
public class TimeStamp {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
//    @ManyToOne
//    @JoinColumn(name ="PROJECT_ID")
//    private Project project ;
    @ManyToOne
    @JoinColumn(name ="TASK_ID")
    private Task task ;
    @Column
    private String time;

    public TimeStamp() {
    }

    public TimeStamp(Task task, String time) {
//        this.project = project;
        this.task = task;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Project getProject() {
//        return project;
//    }
//
//    public void setProject(Project project) {
//        this.project = project;
//    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
