package com.nhnacademy.tdd.parkingsystem.domain;

public class Car {
    String number;
    User driver;

    public Car(String number, User driver) {
        this.number = number;
        this.driver = driver;
    }

    public User getDriver() {
        return driver;
    }

    public String getNumber() {
        return number;
    }
}
