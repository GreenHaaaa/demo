package com.example.arcface.domain.VO;

import com.example.arcface.domain.User;

import java.util.List;


public class UserList {

    private List<User>  list;

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return list.toString();
    }


}