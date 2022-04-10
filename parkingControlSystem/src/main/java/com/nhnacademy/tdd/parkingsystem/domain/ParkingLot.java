package com.nhnacademy.tdd.parkingsystem.domain;

import com.nhnacademy.tdd.parkingsystem.exception.LackMoneyException;
import java.time.Duration;
import java.time.LocalTime;

public class ParkingLot implements Parkable {
    private final ParkingSpaceRepository parkingSpaces;
    private final Entrance entrance;
    private final Exit exit;

    public ParkingLot(ParkingSpaceRepository parkingSpaces, Entrance entrance,
                      Exit exit) {
        this.parkingSpaces = parkingSpaces;
        this.entrance = entrance;
        this.exit = exit;
    }

    @Override
    public User enter(String parkingLotCode, Car car, LocalTime parkingTime) {
        if(parkingLotCode==null){
            throw new IllegalArgumentException("no parkingLotCode");
        }
        if (car == null) {
            throw new IllegalArgumentException("no car");
        }
        User driver = car.getDriver();
        String carNumber = entrance.scan(car);
        ParkingSpace parkingSpace = parkingSpaces.reserveParkingSpace(parkingLotCode, car, parkingTime);

        parkingSpaces.parking(carNumber, parkingSpace);

        return driver;
    }

    @Override
    public User exit(String carNumber, LocalTime endParkingTime) {
        if(carNumber==null){
            throw new IllegalArgumentException("carNumber is null");
        }
        ParkingSpace parkingSpace = parkingSpaces.findByCarNumber(carNumber);

        Car car = parkingSpace.getCar();
        User driver = parkingSpace.getUser();
        Duration parkingTime = calculateParkingTime(parkingSpace,endParkingTime);
        try {
            exit.pay(car, parkingTime);
        } catch (LackMoneyException e) {
            System.out.println(e.getMessage());
        }
        parkingSpaces.delete(carNumber);

        return driver;
    }

    @Override
    public Duration calculateParkingTime(ParkingSpace parkingSpace, LocalTime endParkingTime) {
        LocalTime startParkingTime = parkingSpace.getParkingTime();
        return Duration.between(startParkingTime,endParkingTime);
    }
}
