package com.nhnacademy.tdd.parkingsystem.domain;

public interface Parkable {
    User enter(String parkingLotCode, Car car);

    User exit(String carNumber);
}
