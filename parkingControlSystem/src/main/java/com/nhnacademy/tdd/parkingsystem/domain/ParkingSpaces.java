package com.nhnacademy.tdd.parkingsystem.domain;

import java.util.HashMap;
import java.util.Map;

public class ParkingSpaces implements ParkingSpaceRepository{
    private final Map<String, ParkingSpace> repository = new HashMap<>();
    @Override
    public void insert(String carNumber, ParkingSpace parkingSpace) {
        if(carNumber==null){
            throw new IllegalArgumentException("carNumber is null");
        }
        if(parkingSpace==null){
            throw new IllegalArgumentException("parkingSpace is null");
        }
        repository.put(carNumber,parkingSpace);
    }

    @Override
    public ParkingSpace findByCarNumber(String carNumber) {
        if(carNumber==null){
            throw new IllegalArgumentException("carNumber is null");
        }
        return this.repository.get(carNumber);
    }
}
