package com.nhnacademy.tdd.parkingsystem.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.tdd.parkingsystem.exception.LackMoneyException;
import java.time.Duration;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkingLotTest {
    Parkable parkingLot;
    ParkingSpaceRepository parkingSpaces;
    Entrance entrance;
    Exit exit;

    @BeforeEach
    void setUp() {
        parkingSpaces = mock(ParkingSpaceRepository.class);
        entrance = mock(Entrance.class);
        exit = mock(Exit.class);
        parkingLot = new ParkingLot(parkingSpaces, entrance, exit);
    }

    @DisplayName("주차장에 차가 들어오면 번호판을 인식하고 주차한다.")
    @Test
    void enter() {
        String parkingLotCode = "A-1";

        String number = "99조9999";
        String userId = "semi";
        Money money = new Money(10_000L);
        User driver = new User(userId, money);
        Car car = new Car(number, driver);

        LocalTime startParkingTime = LocalTime.of(14, 00, 00);

        ParkingSpace parkingSpace = new ParkingSpace(parkingLotCode, car, startParkingTime);

        when(entrance.scan(car)).thenReturn(number);
        when(parkingSpaces.reserveParkingSpace(parkingLotCode, car, startParkingTime)).thenReturn(
            parkingSpace);

        User result = parkingLot.enter(parkingLotCode, car, startParkingTime);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);

        verify(entrance).scan(car);
        verify(parkingSpaces).reserveParkingSpace(parkingLotCode, car, startParkingTime);
        verify(parkingSpaces).parking(number, parkingSpace);
    }

    @Test
    void enter_carIsNull_throwsIllegalArgumentException() {
        String parkingLotCode = "A-1";
        Car car = null;

        LocalTime startParkingTime = LocalTime.of(14, 00, 00);

        assertThatThrownBy(() -> parkingLot.enter(parkingLotCode, car, startParkingTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContainingAll("no car");

        verify(entrance, never()).scan(car);
        verify(parkingSpaces, never()).reserveParkingSpace(parkingLotCode, car, startParkingTime);
    }

    @Test
    void enter_parkingLotCodeIsNull_enter_carIsNull_throwsIllegalArgumentException() {
        String parkingLotCode = null;

        String number = "99조9999";
        String userId = "semi";
        Money money = new Money(10_000L);
        User driver = new User(userId, money);
        Car car = new Car(number, driver);

        LocalTime startParkingTime = LocalTime.of(14, 00, 00);

        assertThatThrownBy(() -> parkingLot.enter(parkingLotCode, car, startParkingTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContainingAll("no parkingLotCode");

        verify(entrance, never()).scan(car);
        verify(parkingSpaces, never()).reserveParkingSpace(parkingLotCode, car, startParkingTime);
    }

    @Test
    void exit() throws LackMoneyException {
        String parkingLotCode = "A-1";
        String number = "99조9999";
        String userId = "semi";
        Money money = new Money(10_000L);
        User driver = new User(userId, money);
        Car car = new Car(number, driver);
        LocalTime startParkingTime = LocalTime.of(14, 00, 00);
        ParkingSpace parkingSpace = new ParkingSpace(parkingLotCode, car, startParkingTime);

        LocalTime endParkingTime = LocalTime.of(14, 30, 00);
        Money parkingFee = new Money(1_000);

        Duration parkingTime = parkingLot.calculateParkingTime(parkingSpace,endParkingTime);

        when(parkingSpaces.findByCarNumber(number)).thenReturn(parkingSpace);
        when(exit.pay(car, parkingTime)).thenReturn(parkingFee);

        User result = parkingLot.exit(number, endParkingTime);

        assertThat(result).isNotNull()
            .isEqualTo(driver);

        verify(parkingSpaces).findByCarNumber(number);
        verify(exit).pay(car, parkingTime);
        verify(parkingSpaces).delete(number);
    }

    @Test
    void exit_carNumberIsNull_throwIllegalArgumentException() {
        String carNumber = null;
        LocalTime endParkingTime = LocalTime.of(14, 30, 0);
        assertThatThrownBy(() -> parkingLot.exit(carNumber, endParkingTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContainingAll("carNumber is null");
    }

    @Test
    void calculateParkingTime() {
        String parkingLotCode = "A-1";
        String number = "99조9999";
        String userId = "semi";
        Money money = new Money(10_000L);
        User driver = new User(userId, money);
        Car car = new Car(number, driver);
        LocalTime startParkingTime = LocalTime.of(14, 00, 00);
        ParkingSpace parkingSpace = new ParkingSpace(parkingLotCode, car, startParkingTime);

        LocalTime endParkingTime = LocalTime.of(14, 30, 01);

        Duration parkingTime = parkingLot.calculateParkingTime(parkingSpace, endParkingTime);

        assertThat(parkingTime)
            .isNotNull()
            .isEqualTo(Duration.ofMinutes(30).plusSeconds(1));
    }
}