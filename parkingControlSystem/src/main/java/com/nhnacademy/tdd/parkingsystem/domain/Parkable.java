package com.nhnacademy.tdd.parkingsystem.domain;

import java.time.Duration;
import java.time.LocalTime;

public interface Parkable {
    User enter(String parkingLotCode, Car car, LocalTime parkingTime);

    User exit(String carNumber, LocalTime endParkingTime);

    Duration calculateParkingTime(ParkingSpace parkingSpace, LocalTime endParkingTime);
}
