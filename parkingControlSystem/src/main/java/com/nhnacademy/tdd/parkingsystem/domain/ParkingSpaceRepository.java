package com.nhnacademy.tdd.parkingsystem.domain;

public interface ParkingSpaceRepository {
    void parking(String carNumber, ParkingSpace parkingSpace);

    ParkingSpace findByCarNumber(String carNumber);

    ParkingSpace reserveParkingSpace(String parkingLotCode, Car car);

    void delete(String number);
}
