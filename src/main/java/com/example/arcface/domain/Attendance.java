package com.example.arcface.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @NotNull
    private User user;
    @Column
    private String workDay;
    @Column
    private String onCardTime;
    @Column
    private  String offCardTime;

    public Attendance() {
    }

    public Attendance(@NotNull User user, String workDay, String onCardTime, String offCardTime) {
        this.user = user;
        this.workDay = workDay;
        this.onCardTime = onCardTime;
        this.offCardTime = offCardTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public String getOnCardTime() {
        return onCardTime;
    }

    public void setOnCardTime(String onCardTime) {
        this.onCardTime = onCardTime;
    }

    public String getOffCardTime() {
        return offCardTime;
    }

    public void setOffCardTime(String offCardTime) {
        this.offCardTime = offCardTime;
    }
}
