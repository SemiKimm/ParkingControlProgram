package com.nhnacademy.tdd.parkingsystem.domain;

public class User {
    private final String userId; //TODO UserId로 구현해주기

    public User(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
