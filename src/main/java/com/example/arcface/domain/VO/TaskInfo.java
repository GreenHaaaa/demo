package com.example.arcface.domain.VO;

import com.example.arcface.domain.Project;
import com.example.arcface.domain.Task;
import com.example.arcface.domain.User;

import java.util.List;
import java.util.Set;

public class TaskInfo {
    private List<String > statusChangeTime;

    private long id ;

    private String taskName;



    private Project project ;

    private User taskPublisher;

    private String taskBegin;

    private  String taskEnd;

    private int taskStatus;

    private int securityLv;

    private String taskContent;

    private int workload;

    public TaskInfo(List<String> statusChangeTime, long id, String taskName, Set<User> users, Project project, User taskPublisher, String taskBegin, String taskEnd, int taskStatus, int securityLv, String taskContent, int workload) {
        this.statusChangeTime = statusChangeTime;
        this.id = id;
        this.taskName = taskName;

        this.project = project;
        this.taskPublisher = taskPublisher;
        this.taskBegin = taskBegin;
        this.taskEnd = taskEnd;
        this.taskStatus = taskStatus;
        this.securityLv = securityLv;
        this.taskContent = taskContent;
        this.workload = workload;
    }
    public TaskInfo(List<String> times,Task task){
        this.statusChangeTime = times;
        this.id = task.getId();
        this.taskName = task.getTaskName();

        this.project = task.getProject();
        this.taskPublisher = task.getTaskPublisher();
        this.taskBegin = task.getTaskBegin();
        this.taskEnd = task.getTaskEnd();
        this.taskStatus = task.getTaskStatus();
        this.securityLv = task.getSecurityLv();
        this.taskContent = task.getTaskContent();
        this.workload = task.getWorkload();
    }

    public List<String> getStatusChangeTime() {
        return statusChangeTime;
    }

    public void setStatusChangeTime(List<String> statusChangeTime) {
        this.statusChangeTime = statusChangeTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }



    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getTaskPublisher() {
        return taskPublisher;
    }

    public void setTaskPublisher(User taskPublisher) {
        this.taskPublisher = taskPublisher;
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

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
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
}
