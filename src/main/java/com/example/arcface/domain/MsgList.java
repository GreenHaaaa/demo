package com.example.arcface.domain;

import java.util.ArrayList;
import java.util.List;

public class MsgList {
    private String userName;
    private String time;
    private String context;
    private String userId;
    private Long  taskId;

    public MsgList(String userName, String time, String context, String userId, Long taskId) {
        this.userName = userName;
        this.time = time;
        this.context = context;
        this.userId = userId;
        this.taskId = taskId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    public  static List<MsgList> fromMessages(List<Message> messages)
    {
        List<MsgList> msgLists =  new ArrayList<>();
        for(Message message:messages)
        {
            msgLists.add(new MsgList(message.getUser().getName(),message.getMessageTime(),message.getMessageContent(),message.getUser().getWorkNumber(),message.getTask().getId()));
        }
        return  msgLists;
    }
}
