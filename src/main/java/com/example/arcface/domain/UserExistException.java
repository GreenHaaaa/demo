package com.example.arcface.domain;

public class UserExistException extends RuntimeException{
    private String  id;
    private String tip;
    public UserExistException(String id)
    {
        this.id =id;
        tip = "user who's id "+id+" was already registered";
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }
}

