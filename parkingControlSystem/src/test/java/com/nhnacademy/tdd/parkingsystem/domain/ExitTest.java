package com.nhnacademy.tdd.parkingsystem.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.nhnacademy.tdd.parkingsystem.exception.LackMoneyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExitTest {
    Exit exit;

    @BeforeEach
    void setUp() {
        exit = new Exit();
    }

    @Test
    void pay() throws LackMoneyException {
        int parkingTime = 60*24;
        String number = "99조9999";
        String userId = "semi";
        Money money = new Money(10_000);
        User driver = new User(userId, money);
        Car car = new Car(number, driver);

        Money result = exit.pay(car, parkingTime);

        assertThat(result).isNotNull();
        assertThat(result.getAmount()).isEqualTo(10_000L);
    }

    @Test
    void pay_driverMoneyIsNotEnough_throwLackMoneyException(){
        int parkingTime = 60*24;
        String number = "99조9999";
        String userId = "semi";
        Money money = new Money(5_000);
        User driver = new User(userId, money);
        Car car = new Car(number, driver);

        assertThatThrownBy(()->exit.pay(car,parkingTime))
            .isInstanceOf(LackMoneyException.class)
            .hasMessageContainingAll("money is not enough");
    }

    @Test
    void settleParkingFee(){
        long thirtyMin = 30;
        long fiftyMin = 50;
        long oneHourOneMin = 61;
        long sixHour = 60 * 6;

        Money thirtyMinResult = exit.settleParkingFee(thirtyMin);
        Money fiftyMinResult = exit.settleParkingFee(fiftyMin);
        Money oneHourOneMinResult = exit.settleParkingFee(oneHourOneMin);
        Money sixHourResult = exit.settleParkingFee(sixHour);

        assertThat(thirtyMinResult.getAmount()).isEqualTo(1_000L);
        assertThat(fiftyMinResult.getAmount()).isEqualTo(2_000L);
        assertThat(oneHourOneMinResult.getAmount()).isEqualTo(3_000L);
        assertThat(sixHourResult.getAmount()).isEqualTo(10_000L);
    }
}