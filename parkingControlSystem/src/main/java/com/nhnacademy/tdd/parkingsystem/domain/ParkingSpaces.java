package com.nhnacademy.tdd.parkingsystem.domain;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ParkingSpaces implements ParkingSpaceRepository {
    private final Map<String, ParkingSpace> repository = new HashMap<>();

    @Override
    public void parking(String carNumber, ParkingSpace parkingSpace) {
        if (carNumber == null) {
            throw new IllegalArgumentException("carNumber is null");
        }
        if (parkingSpace == null) {
            throw new IllegalArgumentException("parkingSpace is null");
        }
        repository.put(carNumber, parkingSpace);
    }

    @Override
    public ParkingSpace findByCarNumber(String carNumber) {
        if (carNumber == null) {
            throw new IllegalArgumentException("carNumber is null");
        }
        return this.repository.get(carNumber);
    }

    @Override
    public ParkingSpace reserveParkingSpace(String parkingLotCode, Car car, LocalTime parkingTime) {
        if (parkingLotCode == null) {
            throw new IllegalArgumentException("parkingLotCode is null");
        }
        if (car == null) {
            throw new IllegalArgumentException("car is null");
        }
        return new ParkingSpace(parkingLotCode, car, parkingTime);
    }

    @Override
    public void delete(String number) {
        //TODO
    }
}
