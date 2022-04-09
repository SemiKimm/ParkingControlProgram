package com.nhnacademy.tdd.parkingsystem.domain;

public class ParkingLot implements Parkable {
    private final ParkingSpaceRepository parkingSpaces;
    private final Entrance entrance;

    public ParkingLot(ParkingSpaceRepository parkingSpaces, Entrance entrance) {
        this.parkingSpaces = parkingSpaces;
        this.entrance = entrance;
    }

    @Override
    public User enter(String parkingLotCode, Car car) {
        if(parkingLotCode==null){
            throw new IllegalArgumentException("no parkingLotCode");
        }
        if (car == null) {
            throw new IllegalArgumentException("no car");
        }
        User driver = car.getDriver();
        String carNumber = entrance.scan(car);
        ParkingSpace parkingSpace = parkingSpaces.reserveParkingSpace(parkingLotCode, car);

        parkingSpaces.parking(carNumber, parkingSpace);

        return driver;
    }
}
