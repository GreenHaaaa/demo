package com.example.arcface.domain;

public class taskInfo {
    private String taskName;
    private String owner;
    private int load;
    private String projectName;
    private String time;
    private int lv;

    public taskInfo(String taskName, String owner, int load, String projectName, String time, int lv) {
        this.taskName = taskName;
        this.owner = owner;
        this.load = load;
        this.projectName = projectName;
        this.time = time;
        this.lv = lv;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }
}
