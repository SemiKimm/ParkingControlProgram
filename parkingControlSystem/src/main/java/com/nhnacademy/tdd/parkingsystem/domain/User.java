package com.nhnacademy.tdd.parkingsystem.domain;

import com.nhnacademy.tdd.parkingsystem.exception.LackMoneyException;

public class User {
    private final String userId;
    private Money money;

    public User(String userId, Money money) {
        this.userId = userId;
        this.money = money;
    }

    public String getUserId() {
        return userId;
    }

    public Money spend(long parkingFee) throws LackMoneyException {
        if(parkingFee>this.money.getAmount()){
            throw new LackMoneyException("driver's money is not enough.");
        }
        long balance = money.getAmount() - parkingFee;
        money.setAmount(balance);
        return new Money(parkingFee);
    }
}
