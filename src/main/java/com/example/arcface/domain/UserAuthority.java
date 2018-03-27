package com.example.arcface.domain;

public class UserAuthority {
    private String workNumber;
    private int lv;
    private String name;
    private String depeatment;

    public UserAuthority(String workNumber, int lv, String name, String depeatment) {
        this.workNumber = workNumber;
        this.lv = lv;
        this.name = name;
        this.depeatment = depeatment;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepeatment() {
        return depeatment;
    }

    public void setDepeatment(String depeatment) {
        this.depeatment = depeatment;
    }
}
