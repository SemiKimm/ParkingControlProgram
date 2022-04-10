package com.nhnacademy.tdd.parkingsystem.domain;

public class ParkingSpace {
    private final String code;
    private final Car car;
    private int parkingTime;

    public ParkingSpace(String code, Car car, int parkingTime) {
        this.code = code;
        this.car = car;
        this.parkingTime = parkingTime;
    }

    public String getCode() {
        return code;
    }

    public Car getCar() {
        return car;
    }

    public User getUser() {
        return car.getDriver();
    }

    public int getParkingTime() {
        return parkingTime;
    }
}
