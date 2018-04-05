package com.example.arcface.domain.VO;

import com.example.arcface.domain.User;
import com.example.arcface.domain.UserInfo;

public class UserInfoWithLevel extends UserInfo {
    private int lv;
    public UserInfoWithLevel(User user,int lv){
        super(user);
        this.lv = lv;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }
}
