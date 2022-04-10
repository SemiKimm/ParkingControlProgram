package com.nhnacademy.tdd.parkingsystem.domain;

import java.time.LocalTime;

public interface ParkingSpaceRepository {
    void parking(String carNumber, ParkingSpace parkingSpace);

    ParkingSpace findByCarNumber(String carNumber);

    ParkingSpace reserveParkingSpace(String parkingLotCode, Car car, LocalTime parkingTime);

    void delete(String number);
}
