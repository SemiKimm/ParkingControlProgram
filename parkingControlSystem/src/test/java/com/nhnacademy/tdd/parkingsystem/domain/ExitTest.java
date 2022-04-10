package com.nhnacademy.tdd.parkingsystem.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.nhnacademy.tdd.parkingsystem.exception.LackMoneyException;
import java.time.Duration;
import java.time.LocalTime;
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
        LocalTime startParkingTime = LocalTime.of(14, 30, 0);
        LocalTime endParkingTime = LocalTime.of(14, 30, 1);
        Duration parkingTime = Duration.between(startParkingTime,endParkingTime);

        String number = "99조9999";
        String userId = "semi";
        Money money = new Money(10_000);
        User driver = new User(userId, money);
        Car car = new Car(number, driver);

        Money result = exit.pay(car, parkingTime);

        assertThat(result).isNotNull();
        assertThat(result.getAmount()).isEqualTo(1_000L);
    }

    @Test
    void pay_driverMoneyIsNotEnough_throwLackMoneyException() {
        LocalTime startParkingTime = LocalTime.of(14, 30, 0);
        LocalTime endParkingTime = LocalTime.of(20, 00, 00);
        Duration parkingTime = Duration.between(startParkingTime,endParkingTime);

        String number = "99조9999";
        String userId = "semi";
        Money money = new Money(5_000);
        User driver = new User(userId, money);
        Car car = new Car(number, driver);

        assertThatThrownBy(() -> exit.pay(car, parkingTime))
            .isInstanceOf(LackMoneyException.class)
            .hasMessageContainingAll("money is not enough");
    }

    @Test
    void settleParkingFee_parkingTimeIsThirty() {
        Duration parkingTime = Duration.ofMinutes(30);

        Money result = exit.settleParkingFee(parkingTime);
        assertThat(result.getAmount()).isEqualTo(1_000L);
    }

    @Test
    void settleParkingFee_parkingTimePlusTen(){
        Duration parkingTime = Duration.ofMinutes(30).plusSeconds(1);

        Money result = exit.settleParkingFee(parkingTime);
        assertThat(result.getAmount()).isEqualTo(1_500L);
    }

    @Test
    void settleParkingFee_parkingTimeIsFiftyMin(){
        Duration parkingTime = Duration.ofMinutes(50);

        Money result = exit.settleParkingFee(parkingTime);
        assertThat(result.getAmount()).isEqualTo(2_000L);
    }

    @Test
    void settleParkingFee_parkingTimeIsOneHourOneMin(){
        Duration parkingTime = Duration.ofHours(1).plusMinutes(1);

        Money result = exit.settleParkingFee(parkingTime);
        assertThat(result.getAmount()).isEqualTo(3_000L);
    }

    @Test
    void settleParkingFee_parkingTimeIsSixHours(){
        Duration parkingTime = Duration.ofHours(6);

        Money result = exit.settleParkingFee(parkingTime);
        assertThat(result.getAmount()).isEqualTo(10_000L);
    }

    @Test
    void settleParkingFee_parkingTimeIs2Days(){
        Duration parkingTime = Duration.ofHours(6);

        Money result = exit.settleParkingFee(parkingTime);
        assertThat(result.getAmount()).isEqualTo(10_000L);
    }


}