package com.example.arcface.domain.VO;

public class TaskUserDetail {
    private String userid;
    private String name;
    private String company;
    private int workTime;

    public TaskUserDetail(String name, String company, int workTime,String userid) {
        this.name = name;
        this.company = company;
        this.workTime = workTime;
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
