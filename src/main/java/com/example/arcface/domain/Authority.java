package com.example.arcface.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.sql.Insert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;
@Entity
public class Authority {
    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator="idGenerator")
    private String  id;
    @ManyToOne()
    @JoinColumn(name="user_id" )
    @NotNull
    private User user;
    @ManyToOne
    @JoinColumn(name="project_id")
    @NotNull
    private Project project;
    @Column
    private int authoity;
    public Authority(){};
    public Authority(@NotNull User user, @NotNull Project project, int authoity) {
        this.user = user;
        this.project = project;
        this.authoity = authoity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getAuthoity() {
        return authoity;
    }

    public void setAuthoity(int authoity) {
        this.authoity = authoity;
    }
}
