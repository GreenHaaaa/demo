package com.example.arcface.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {
    @Id
    private String workNumber;
    @Column
    @JsonIgnore
    private String password;
    @JsonIgnore
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(length=65000)
    private byte[] faceFeature;
    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private Set<Task> tasks;
    @JsonIgnore
    @Column
    private String exp;
    @JsonIgnore
    @Column
    private Date lastPasswordResetDate;
    @Column
    private String name;
    @Column
    @JsonIgnore
    private String skills;
    @Column
    private String company;
    @Column
    private String tel;
    @Column
    private String  email;
    @Column
    private String  department;
    @Column
    private String offer;
    @JsonIgnore
    @Column
    private String role;
    @Column
    private int status;

    public User()
    {

    }

    public User(String workNumber, String password, byte[] faceFeature, String name, String tel, String email, String department, String offer,String exp,String skills,String company) {
        this.workNumber = workNumber;
        this.password = password;
        this.faceFeature = faceFeature;
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.department = department;
        this.offer = offer;
        this.exp  =exp;
        this.company = company;
        this.skills = skills;
        this.status=0;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getFaceFeature() {
        return faceFeature;
    }

    public void setFaceFeature(byte[] faceFeature) {
        this.faceFeature = faceFeature;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
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

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getStatus() == user.getStatus() &&
                Objects.equals(getWorkNumber(), user.getWorkNumber()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Arrays.equals(getFaceFeature(), user.getFaceFeature()) &&
                Objects.equals(getTasks(), user.getTasks()) &&
                Objects.equals(getExp(), user.getExp()) &&
                Objects.equals(getLastPasswordResetDate(), user.getLastPasswordResetDate()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getSkills(), user.getSkills()) &&
                Objects.equals(getCompany(), user.getCompany()) &&
                Objects.equals(getTel(), user.getTel()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getDepartment(), user.getDepartment()) &&
                Objects.equals(getOffer(), user.getOffer()) &&
                Objects.equals(getRole(), user.getRole());
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(getWorkNumber(), getPassword(), getTasks(), getExp(), getLastPasswordResetDate(), getName(), getSkills(), getCompany(), getTel(), getEmail(), getDepartment(), getOffer(), getRole(), getStatus());
        result = 31 * result + Arrays.hashCode(getFaceFeature());
        return result;
    }
}
