package com.example.arcface.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;
@Entity
public class Project {
    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator="idGenerator")
    private String id;
    @Column(length = 1000)
    private String projectContent;
    @Column
    private String department;
    @Column
    private int sevurityLv;
    @Column
    private String projectBegin;
    @Column
    private String projectEnd;
    @Column
    private int projectStatus;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    public Project() {
    }

    public Project(String projectContent, String department, int sevurityLv, String projectBegin, String projectEnd, int prokectStatus,User user) {

        this.projectContent = projectContent;
        this.department = department;
        this.sevurityLv = sevurityLv;
        this.projectBegin = projectBegin;
        this.projectEnd = projectEnd;
        this.projectStatus = prokectStatus;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSevurityLv() {
        return sevurityLv;
    }

    public void setSevurityLv(int sevurityLv) {
        this.sevurityLv = sevurityLv;
    }

    public String getProjectBegin() {
        return projectBegin;
    }

    public void setProjectBegin(String projectBegin) {
        this.projectBegin = projectBegin;
    }

    public String getProjectEnd() {
        return projectEnd;
    }

    public void setProjectEnd(String projectEnd) {
        this.projectEnd = projectEnd;
    }

    public int getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(int projectStatus) {
        this.projectStatus = projectStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
