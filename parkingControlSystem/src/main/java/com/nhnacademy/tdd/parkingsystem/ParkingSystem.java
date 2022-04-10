package com.nhnacademy.tdd.parkingsystem;

import com.nhnacademy.tdd.parkingsystem.domain.Car;
import com.nhnacademy.tdd.parkingsystem.domain.Parkable;
import com.nhnacademy.tdd.parkingsystem.domain.User;
import com.nhnacademy.tdd.parkingsystem.domain.UserRepository;

class ParkingSystem {
    private final Parkable parkingLot;
    private final UserRepository userRepository;

    public ParkingSystem(Parkable parkingLot,
                         UserRepository userRepository) {
        this.parkingLot = parkingLot;
        this.userRepository = userRepository;
    }

    User comeIn(Car car, String parkingLotCode, int parkingTime) {
        if(car==null){
            throw new IllegalArgumentException("no car");
        }
        User driver = parkingLot.enter(parkingLotCode, car, parkingTime);
        userRepository.insert(driver);
        return driver;
    }

    User comeOut(String carNumber) {
        if(carNumber==null){
            throw new IllegalArgumentException("carNumber is null");
        }
        User driver = parkingLot.exit(carNumber);
        userRepository.delete(driver);
        return driver;
    }
}
