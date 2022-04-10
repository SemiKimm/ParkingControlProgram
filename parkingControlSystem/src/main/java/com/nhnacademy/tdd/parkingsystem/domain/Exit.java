package com.nhnacademy.tdd.parkingsystem.domain;

import com.nhnacademy.tdd.parkingsystem.exception.LackMoneyException;

public class Exit {
    public Money pay(Car car, int time) throws LackMoneyException {
        User driver = car.getDriver();
        Money parkingFee = settleParkingFee(time);
        return driver.spend(parkingFee.getAmount());
    }

    public Money settleParkingFee(long parkingTime) {
        Money parkingFee = new Money();

        if (parkingTime <= 30) {
            parkingFee.setAmount(1_000L);
        }
        if (parkingTime > 30) {
            parkingTime = parkingTime - 30;
            int count = (int) (parkingTime / 10);
            if (parkingTime % 10 != 0) {
                count++;
            }
            long amount = 1_000L + 500L * count;
            if(amount>10_000L){
                amount = 10_000L;
            }
            parkingFee.setAmount(amount);
        }
        if (parkingTime == 24 * 60) {
            parkingFee.setAmount(10_000L);
        }
        return parkingFee;
    }
}
