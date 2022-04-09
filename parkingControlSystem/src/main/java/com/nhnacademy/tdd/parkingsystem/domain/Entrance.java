package com.nhnacademy.tdd.parkingsystem.domain;

public class Entrance {
    public String scan(Car car) {
        if(car==null){
            throw new IllegalArgumentException("no car");
        }
        return car.getNumber();
    }
}
