package com.nhnacademy.tdd.parkingsystem.domain;

import com.nhnacademy.tdd.parkingsystem.exception.LackMoneyException;
import java.time.Duration;

public class Exit {
    public Money pay(Car car, Duration parkingTime) throws LackMoneyException {
        User driver = car.getDriver();
        Money parkingFee = settleParkingFee(parkingTime);
        return driver.spend(parkingFee.getAmount());
    }

    public Money settleParkingFee(Duration parkingTime) {
        Money parkingFee = new Money();

        if (parkingTime.compareTo(Duration.ofMinutes(30)) < 0 ||
            parkingTime.compareTo(Duration.ofMinutes(30)) == 0) {
            parkingFee.setAmount(1_000L);
        }
        if (parkingTime.compareTo(Duration.ofMinutes(30)) > 0) {
            Duration exceptThirtyMin = parkingTime.minusMinutes(30);
            int count = 0;
            while (exceptThirtyMin.compareTo(Duration.ZERO) > 0) {
                count++;
                exceptThirtyMin = exceptThirtyMin.minus(Duration.ofMinutes(10));
            }
            parkingFee.setAmount(1_000L + 500L * count);
        }
        if (parkingFee.getAmount() > 10_000L) {
            parkingFee.setAmount(10_000L);
        }
        if(parkingTime.compareTo(Duration.ofDays(1))>0){
            parkingFee.setAmount(20_000L);
        }
        return parkingFee;
    }
}
