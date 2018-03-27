package com.example.arcface.domain;

import org.springframework.security.core.parameters.P;

import java.util.Arrays;
import java.util.List;

public class UserInfo {
    private String workNumber;
    private List<String> exp;
    private String name;
    private String tel;
    private String email;
    private String offer;
    private String department;
    private List<String> skills;
    private String company;

    public UserInfo(String workNumber, List<String> exp, String name, String tel, String email, String offer, String department, List<String> skills) {
        this.workNumber = workNumber;
        this.exp = exp;
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.offer = offer;
        this.department = department;
        this.skills = skills;

    }
    public UserInfo(User user)
    {
        this.workNumber = user.getWorkNumber();
        this.name =user.getName();
        this.tel =user.getTel();
        this.email = user.getEmail();
        this.offer = user.getOffer();
        this.department = user.getDepartment();
        this.exp = Arrays.asList(user.getExp().split("&&"));
        this.skills = Arrays.asList(user.getSkills().split("&&"));
        this.company = user.getCompany();
    }
    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public List<String> getExp() {
        return exp;
    }

    public void setExp(List<String> exp) {
        this.exp = exp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
