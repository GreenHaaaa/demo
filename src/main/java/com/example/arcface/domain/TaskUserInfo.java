package com.example.arcface.domain;

import javax.persistence.*;

@Entity
public class TaskUserInfo {
    @Id  @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private String userId;
    @Column
    private Long taskId;
    @Column
    private int workHours;

    public TaskUserInfo(Long id, String userId, Long taskId, int workHours) {
        this.id = id;
        this.userId = userId;
        this.taskId = taskId;
        this.workHours = workHours;
    }

    public TaskUserInfo() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public int getWorkHours() {
        return workHours;
    }

    public void setWorkHours(int workHours) {
        this.workHours = workHours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
