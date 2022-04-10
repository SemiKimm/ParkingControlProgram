package com.nhnacademy.tdd.parkingsystem.domain;

import java.time.LocalTime;

public class ParkingSpace {
    private final String code;
    private final Car car;
    private LocalTime parkingTime;

    public ParkingSpace(String code, Car car, LocalTime parkingTime) {
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

    public LocalTime getParkingTime() {
        return parkingTime;
    }
}
