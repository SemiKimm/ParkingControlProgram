package com.nhnacademy.tdd.parkingsystem.domain;

public interface ParkingSpaceRepository {
    void insert(String carNumber, ParkingSpace parkingSpace);

    ParkingSpace findByCarNumber(String carNumber);
}
