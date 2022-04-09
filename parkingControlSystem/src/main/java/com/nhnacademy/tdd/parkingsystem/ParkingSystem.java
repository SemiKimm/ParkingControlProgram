package com.nhnacademy.tdd.parkingsystem;

import com.nhnacademy.tdd.parkingsystem.domain.Car;
import com.nhnacademy.tdd.parkingsystem.domain.Parkable;
import com.nhnacademy.tdd.parkingsystem.domain.User;
import com.nhnacademy.tdd.parkingsystem.domain.UserRepository;

class ParkingSystem {
    Parkable parkingLot;
    UserRepository userRepository;

    public ParkingSystem(Parkable parkingLot, UserRepository userRepository) {
        this.parkingLot = parkingLot;
        this.userRepository = userRepository;
    }

    User comeIn(Car car) {
        if(car==null){
            throw new IllegalArgumentException("no car");
        }
        User driver = parkingLot.enter(car);
        return driver;
    }
}
