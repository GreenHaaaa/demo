package com.example.arcface.config.constraints;

public enum ReturnMessaage {
    FORBIDDEN("没有权限进行操作"),
    NOT_FOUND("没有这个东西");


    private String name ;


    private ReturnMessaage( String name ){
        this.name = name ;

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
