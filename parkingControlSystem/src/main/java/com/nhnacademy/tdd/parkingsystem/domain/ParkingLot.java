package com.nhnacademy.tdd.parkingsystem.domain;

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

    @Override
    public User exit(String carNumber) {
        if(carNumber==null){
            throw new IllegalArgumentException("carNumber is null");
        }
        ParkingSpace parkingSpace = parkingSpaces.findByCarNumber(carNumber);

        Car car = parkingSpace.getCar();
        User driver = parkingSpace.getUser();
        exit.pay(car);
        parkingSpaces.delete(carNumber);

        return driver;
    }
}
