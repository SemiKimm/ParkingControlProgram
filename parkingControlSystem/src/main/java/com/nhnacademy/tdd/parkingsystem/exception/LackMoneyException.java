package com.nhnacademy.tdd.parkingsystem.exception;

public class LackMoneyException
    extends Exception {
    public LackMoneyException(String message) {
        super(message);
    }
}
