package com.example.arcface.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mId;
    @ManyToOne
    @JoinColumn(name = "FROM_USER_ID")
    @NotNull
    private User user;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name ="TASK_ID")
    private Task task;
    @Column
    private String messageContent;
    @Column
    private String messageTime;
    @ManyToOne
    @JoinColumn(name = "TO_USER_ID")
    @NotNull
    private User toUser;
    public Message() {
    }

    public Message( @NotNull User user, String messageContent, String messageTime ,Task task,User user2) {

        this.user = user;
        this.messageContent = messageContent;
        this.messageTime = messageTime;
        this.task = task;
        this.toUser = user2;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }




}
