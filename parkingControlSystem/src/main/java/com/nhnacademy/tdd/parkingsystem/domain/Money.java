package com.nhnacademy.tdd.parkingsystem.domain;

public class Money {
    private long amount;
    public Money(){
        this(0L);
    }
    public Money(long amount) {
        this.amount = amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return this.amount;
    }
}
