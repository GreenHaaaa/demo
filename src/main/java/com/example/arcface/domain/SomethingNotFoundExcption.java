package com.example.arcface.domain;

import java.util.NoSuchElementException;

public class SomethingNotFoundExcption extends NoSuchElementException {
    private String item;

    public SomethingNotFoundExcption(String item) {
        this.item = item;
    }

    public SomethingNotFoundExcption(String s, String item) {
        super(s);
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
