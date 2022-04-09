package com.nhnacademy.tdd.parkingsystem.domain;

public class ParkingLot implements Parkable {
    private final ParkingSpaceRepository parkingSpaces;
    private final Entrance entrance;
    public ParkingLot(ParkingSpaceRepository parkingSpaces, Entrance entrance) {
        this.parkingSpaces = parkingSpaces;
        this.entrance = entrance;
    }

    @Override
    public User enter(Car car) {
        if(car==null){
            throw new IllegalArgumentException("no car");
        }
        //TODO 스캔한 번호가 parkingSpaces에 저장되는거 구현해야됨(map으로 key는 scan한 번호, value에 car 객체 넣기)
        entrance.scan(car);
        User driver = car.getDriver();
        return driver;
    }
}
